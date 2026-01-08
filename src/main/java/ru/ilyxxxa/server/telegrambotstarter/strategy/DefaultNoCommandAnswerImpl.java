package ru.ilyxxxa.server.telegrambotstarter.strategy;

import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultNoCommandAnswerImpl extends NoCommandAnswer {

    @Override
    public String answer(Message message) {
        return "Введите команду, например /start";
    }
}
