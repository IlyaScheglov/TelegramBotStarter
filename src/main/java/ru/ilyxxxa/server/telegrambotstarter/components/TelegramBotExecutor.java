package ru.ilyxxxa.server.telegrambotstarter.components;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ilyxxxa.server.telegrambotstarter.exception.TelegramBotExecutionMessageException;
import ru.ilyxxxa.server.telegrambotstarter.properties.TelegramBotProperties;

public class TelegramBotExecutor extends TelegramLongPollingBot {

    private final TelegramBotProperties botProperties;
    private final BotStrategy botStrategy;

    public TelegramBotExecutor(TelegramBotProperties botProperties, BotStrategy botStrategy) {
        this.botProperties = botProperties;
        this.botStrategy = botStrategy;
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken(){
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processMessage(update.getMessage());
        }
    }

    public void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new TelegramBotExecutionMessageException("Ошибка при отправке сообщения", e);
        }
    }

    private void processMessage(Message message) {
        SendMessage sendMessage = botStrategy.getSendMessageByStrategy(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new TelegramBotExecutionMessageException("Ошибка при отправке сообщения", e);
        }
    }
}
