package com.hykhd.mirai.plugin

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.code.MiraiCode.deserializeMiraiCode
import net.mamoe.mirai.message.data.*
import java.util.*

object GroupEvent : SimpleListenerHost() {

    private var groupMsgSaveMap = TreeMap<Long, GroupMsgSave>()


    @EventHandler
    suspend fun onGroupMessageEvent(event: GroupMessageEvent) {
        val message = event.message
        val group = event.group

        val groupMsgSave = groupMsgSaveMap.getOrPut(group.id) { GroupMsgSave(coroutineContext, group.id) }
        groupMsgSave.addMsgByIds(message.source.ids, message.serializeToMiraiCode())

        if (groupMsgSave.isRepeat(message = message.serializeToMiraiCode())) {
            group.sendMessage(message)
        }
        message.findIsInstance<FlashImage>()?.let { group.sendMessage(it.image) }
    }

    @EventHandler
    suspend fun onBotInvitedJoinGroupRequestEvent(event: BotInvitedJoinGroupRequestEvent) { // 可以抛出任何异常, 将在 handleException 处理
        event.accept()
    }

    @EventHandler
    suspend fun onGroupRecall(eventGroupRecall: MessageRecallEvent.GroupRecall) {
        val group = eventGroupRecall.group
        val author = eventGroupRecall.author
        val operator = eventGroupRecall.operator
        groupMsgSaveMap[group.id]?.let { groupMsgSave ->
            groupMsgSave.getMsgByIds(eventGroupRecall.messageIds)?.let {
                DanBot.logger.debug("撤回消息内容:$it")

                val messageChain =
                    At(eventGroupRecall.operator!!).plus(PlainText("你撤回你🐴呢？")).plus(it.deserializeMiraiCode())
                val messageReceipt = group.sendMessage(messageChain)
                groupMsgSave.addMsgByIds(messageReceipt.source.ids, messageChain.serializeToMiraiCode())
            }
        }

    }
}