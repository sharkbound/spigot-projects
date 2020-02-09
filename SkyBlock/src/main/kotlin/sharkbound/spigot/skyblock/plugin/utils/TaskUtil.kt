package sharkbound.spigot.skyblock.plugin.utils

import sharkbound.spigot.skyblock.plugin.server
import sharkbound.spigot.skyblock.plugin.skyBlockInstance

data class CancellableTask(val taskId: Int) {
    val successful
        get() = taskId != -1

    val failed
        get() = !successful

    fun cancel() {
        server.scheduler.cancelTask(taskId)
    }
}

fun delaySyncTask(tickDelay: Long, task: () -> Unit): CancellableTask {
    return CancellableTask(server.scheduler.scheduleSyncDelayedTask(skyBlockInstance, task, tickDelay))
}

fun repeatingSyncTask(startDelay: Long, intervalTicks: Long, handler: () -> Unit): CancellableTask =
    CancellableTask(server.scheduler.scheduleSyncRepeatingTask(skyBlockInstance, handler, startDelay, intervalTicks))

fun cancellingRepeatingSyncTask(
    startDelay: Long,
    intervalTicks: Long,
    shouldCancel: () -> Boolean,
    handler: () -> Unit
): CancellableTask {
    var task = CancellableTask(-1)
    task = repeatingSyncTask(startDelay, intervalTicks) {
        handler()
        if (shouldCancel()) {
            task.cancel()
        }
    }
    return task
}

fun startCountDown(
    initialValue: Int,
    startDelay: Long = 0,
    intervalTicks: Long = 20,
    handler: (Int) -> Unit
): CancellableTask {
    var i = initialValue
    return cancellingRepeatingSyncTask(startDelay, intervalTicks, { i == -1 }) {
        handler(i)
        i -= 1
    }
//    task = CancellableTask(server.scheduler.scheduleSyncRepeatingTask(skyBlockInstance, {
//        handler(i)
//        i -= 1
//        if (i == -1) {
//            task.cancel()
//        }
//    }, startDelay, intervalTicks))
//    return task
}