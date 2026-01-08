package ru.ilyxxxa.server.telegrambotstarter.exception;

public class TelegramBotExecutionMessageException extends RuntimeException {

    public TelegramBotExecutionMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramBotExecutionMessageException(String message) {
        super(message);
    }

    public TelegramBotExecutionMessageException() {
        super("Ошибка работы телеграм бота");
    }
}
