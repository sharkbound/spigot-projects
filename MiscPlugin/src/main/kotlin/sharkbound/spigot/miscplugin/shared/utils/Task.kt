package sharkbound.spigot.miscplugin.shared.utils

import sharkbound.spigot.miscplugin.shared.instance
import sharkbound.spigot.miscplugin.shared.server

open class CancellableTask(var taskId: Int) {
    open val successful
        get() = taskId != -1
    open val failed
        get() = !successful

    open fun cancel() {
        server.scheduler.cancelTask(taskId)
    }
}

fun delaySyncTask(tickDelay: Long = 0, task: () -> Unit): CancellableTask {
    return CancellableTask(server.scheduler.scheduleSyncDelayedTask(instance, task, tickDelay))
}

fun repeatingSyncTask(startDelay: Long = 0, intervalTicks: Long = 0, handler: () -> Unit): CancellableTask =
    CancellableTask(server.scheduler.scheduleSyncRepeatingTask(instance, handler, startDelay, intervalTicks))

fun cancellingRepeatingSyncTask(
    startDelay: Long = 0,
    intervalTicks: Long = 0,
    shouldCancel: (CancellableTask.() -> Boolean)? = null,
    onCancel: (CancellableTask.() -> Unit)? = null,
    handler: CancellableTask.() -> Unit
): CancellableTask {
    var task = CancellableTask(-1)
    task = repeatingSyncTask(startDelay, intervalTicks) {
        task.handler()
        if (shouldCancel?.invoke(task) == true) {
            task.cancel()
            onCancel?.invoke(task)
        }
    }
    return task
}