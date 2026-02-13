package ru.ilyxxxa.server.telegrambotstarter.components;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ilyxxxa.server.telegrambotstarter.strategy.AnswerStrategy;
import ru.ilyxxxa.server.telegrambotstarter.strategy.CallbackResponseStrategy;
import ru.ilyxxxa.server.telegrambotstarter.strategy.DefaultCallbackResponseImpl;
import ru.ilyxxxa.server.telegrambotstarter.strategy.DefaultNoCommandAnswerImpl;
import ru.ilyxxxa.server.telegrambotstarter.strategy.NoCommandAnswer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BotStrategy {

    private final NoCommandAnswer noCommandAnswer;
    private final Map<String, AnswerStrategy> strategiesMap;
    private final Map<String, CallbackResponseStrategy> callbackResponseStrategyMap;

    public BotStrategy(List<AnswerStrategy> strategies,
                       List<CallbackResponseStrategy> callbackStrategies) {
        this.strategiesMap = strategies.stream()
                .filter(strategy -> Objects.nonNull(strategy.command()))
                .collect(Collectors.toMap(AnswerStrategy::command, Function.identity()));
        this.noCommandAnswer = strategies.stream()
                .filter(strategy -> Objects.isNull(strategy.command()))
                .filter(strategy -> strategy instanceof NoCommandAnswer)
                .map(strategy -> (NoCommandAnswer) strategy)
                .findFirst()
                .orElseGet(DefaultNoCommandAnswerImpl::new);
        this.callbackResponseStrategyMap = callbackStrategies.stream()
                .collect(Collectors.toMap(CallbackResponseStrategy::id, Function.identity()));
    }

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
        this.callbackResponseStrategyMap = Collections.emptyMap();
    }

    public SendMessage getSendMessageByStrategy(Message message) {
        AnswerStrategy strategy = strategiesMap.getOrDefault(message.getText(), noCommandAnswer);
        return strategy.answer(message);
    }

    public EditMessageText getEditMessageByStrategy(CallbackQuery callbackQuery) {
        CallbackResponseStrategy strategy = callbackResponseStrategyMap.getOrDefault(callbackQuery.getData(), new DefaultCallbackResponseImpl());
        return strategy.callbackEditMessage(callbackQuery);
    }
}
