package ru.ilyxxxa.server.telegrambotstarter.strategy;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface AnswerStrategy {

    String command();
    String answer(Message message);
}
