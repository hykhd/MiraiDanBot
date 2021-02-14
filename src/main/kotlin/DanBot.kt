package com.hykhd.mirai.plugin

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.info


const val ID = "com.hykhd.mirai.danbot"
const val VERSION = "0.1.0"
const val NAME = "MiraiDanBotPlugin"
const val AUTHOR = "hykhd"

object DanBot : KotlinPlugin(
    JvmPluginDescription(
        id = ID,
        version = VERSION
    ){
        name(NAME)
        author(AUTHOR)
    }
) {
    override fun onEnable() {
        logger.info { "DanBot loaded" }
        globalEventChannel().subscribeAlways<FriendMessageEvent> {
            sender.sendMessage(message)
        }

        globalEventChannel().registerListenerHost(GroupEvent)
    }

}