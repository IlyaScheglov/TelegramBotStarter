package ru.ilyxxxa.server.telegrambotstarter.components;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ilyxxxa.server.telegrambotstarter.strategy.AnswerStrategy;
import ru.ilyxxxa.server.telegrambotstarter.strategy.DefaultNoCommandAnswerImpl;
import ru.ilyxxxa.server.telegrambotstarter.strategy.NoCommandAnswer;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BotStrategy {

    private final NoCommandAnswer noCommandAnswer;
    private final Map<String, AnswerStrategy> strategiesMap;

    public BotStrategy(List<AnswerStrategy> strategies) {
        this.strategiesMap = strategies.stream()
                .filter(strategy -> Objects.nonNull(strategy.command()))
                .collect(Collectors.toMap(AnswerStrategy::command, Function.identity()));
        this.noCommandAnswer = strategies.stream()
                .filter(strategy -> Objects.isNull(strategy.command()))
                .filter(strategy -> strategy instanceof NoCommandAnswer)
                .map(strategy -> (NoCommandAnswer) strategy)
                .findFirst()
                .orElseGet(DefaultNoCommandAnswerImpl::new);
    }

    public SendMessage getSendMessageByStrategy(Message message) {
        AnswerStrategy strategy = strategiesMap.getOrDefault(message.getText(), noCommandAnswer);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(strategy.answer(message))
                .build();
    }
}
