package com.hykhd.mirai.plugin

import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader
import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol.ANDROID_PAD

suspend fun main() {
    MiraiConsoleTerminalLoader.startAsDaemon()

    DanBot.load()
    DanBot.enable()

    MiraiConsole.job.join()
}