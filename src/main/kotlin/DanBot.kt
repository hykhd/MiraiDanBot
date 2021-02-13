package com.hykhd.mirai.plugin

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.event.subscribeFriendMessages
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.info
import java.util.*

object DanBot : KotlinPlugin(
    JvmPluginDescription(
        id = "com.hykhd.mirai.plugin",
        name = "DanBot",
        version = "0.1.0"
    )
) {
    var RepeaterMap = TreeMap<Long,Repeater>()
    override fun onEnable() {
        logger.info { "DanBot loaded" }
        globalEventChannel().subscribeAlways<FriendMessageEvent> {
            logger.info{ message.content.toString()}
            sender.sendMessage(message)
        }
        globalEventChannel().subscribeAlways<GroupMessageEvent> {
            val repeater =  RepeaterMap.getOrPut(group.id){ Repeater(group.id)}
            if (repeater.isRepeat(message = message.contentToString())){
                group.sendMessage(message)
            }
        }
    }

}