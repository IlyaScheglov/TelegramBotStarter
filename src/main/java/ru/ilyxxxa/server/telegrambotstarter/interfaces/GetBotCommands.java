package ru.ilyxxxa.server.telegrambotstarter.interfaces;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface GetBotCommands {

    List<BotCommand> getBotCommands();
}
