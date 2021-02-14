package com.hykhd.mirai.plugin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import kotlin.collections.HashMap
import kotlin.coroutines.CoroutineContext

class GroupMsgSave(override val coroutineContext: CoroutineContext, groupNum: Long): CoroutineScope {
    var groupNum: Long = groupNum
    var msgSave = HashMap<Int, String>()

    var lastMessage: String = ""
        get() = field
        set
    var last2Message: String = ""
        private set

    fun getMsgByIds(messageIds: IntArray): String? {
        if (messageIds.size == 1){
            DanBot.logger.debug("get messageIds:${messageIds[0]}")
            return msgSave[messageIds[0]]
        }
        return null
    }

    fun addMsgByIds(messageIds: IntArray, message: String) {
        if (messageIds.size == 1){
            DanBot.logger.debug("add messageIds:${messageIds[0]} message:$message")
            msgSave[messageIds[0]] = message
            launch {
                delay(Duration.ofMinutes(10).toMillis())
                DanBot.logger.debug("超时删除消息:${messageIds[0]}")
                msgSave.remove(messageIds[0])
            }
        }


    }

    fun isRepeat(message: String): Boolean {
        var flag: Boolean = false
        if (message == lastMessage && message != last2Message)
            flag = true
        last2Message = lastMessage
        lastMessage = message

        DanBot.logger.debug("last2Message:" + last2Message + " lastMessage:" + lastMessage)
        return flag
    }
}