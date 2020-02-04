package sharkbound.spigot.skyblock.plugin.utils

import sharkbound.spigot.skyblock.plugin.server
import sharkbound.spigot.skyblock.plugin.skyBlockInstance

data class TaskResult(val taskId: Int) {
    val successful
        get() = taskId != -1

    val failed
        get() = taskId == -1

    fun cancel() {
//        println("CANCELLED: $taskId")
        server.scheduler.cancelTask(taskId)
    }
}

fun delaySyncTask(tickDelay: Long, task: () -> Unit): TaskResult {
    return TaskResult(server.scheduler.scheduleSyncDelayedTask(skyBlockInstance, task, tickDelay))
}


fun startCountDown(
    initialValue: Int,
    startDelay: Long = 0,
    intervalTicks: Long = 20,
    handler: (Int) -> Unit
): TaskResult {
    var i = initialValue
    var task = TaskResult(-2)
    task = TaskResult(server.scheduler.scheduleSyncRepeatingTask(skyBlockInstance, {
        handler(i)
        i -= 1
        if (i == -1) {
            task.cancel()
        }
    }, startDelay, intervalTicks))
    return task
}