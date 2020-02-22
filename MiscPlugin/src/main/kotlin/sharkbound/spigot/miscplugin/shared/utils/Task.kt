package sharkbound.spigot.miscplugin.shared.utils

import sharkbound.spigot.miscplugin.shared.instance
import sharkbound.spigot.miscplugin.shared.server

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
    return CancellableTask(server.scheduler.scheduleSyncDelayedTask(instance, task, tickDelay))
}

fun repeatingSyncTask(startDelay: Long, intervalTicks: Long, handler: () -> Unit): CancellableTask =
    CancellableTask(server.scheduler.scheduleSyncRepeatingTask(instance, handler, startDelay, intervalTicks))

fun cancellingRepeatingSyncTask(
    startDelay: Long,
    intervalTicks: Long,
    shouldCancel: (() -> Boolean)? = null,
    handler: () -> Unit
): CancellableTask {
    var task = CancellableTask(-1)
    task = repeatingSyncTask(startDelay, intervalTicks) {
        handler()
        if (shouldCancel?.invoke() == true) {
            task.cancel()
        }
    }
    return task
}