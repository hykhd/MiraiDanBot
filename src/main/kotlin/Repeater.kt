package com.hykhd.mirai.plugin

import net.mamoe.mirai.utils.info

class Repeater(groupNum: Long) {
    var groupNum: Long = groupNum


    var lastMessage: String = ""
        get() = field
        set
    var last2Message: String = ""
        private set

    fun isRepeat(message: String): Boolean {
        var flag:Boolean = false
        if (message == lastMessage && message != last2Message)
            flag = true
        last2Message = lastMessage
        lastMessage = message

        DanBot.logger.info("last2Message:" + last2Message + " lastMessage:" + lastMessage)
        return flag
    }

}
