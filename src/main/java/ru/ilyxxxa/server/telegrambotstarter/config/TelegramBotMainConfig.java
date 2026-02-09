package ru.ilyxxxa.server.telegrambotstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ilyxxxa.server.telegrambotstarter.components.BotInitializer;
import ru.ilyxxxa.server.telegrambotstarter.components.BotStrategy;
import ru.ilyxxxa.server.telegrambotstarter.interfaces.GetBotCommands;
import ru.ilyxxxa.server.telegrambotstarter.components.TelegramBotExecutor;
import ru.ilyxxxa.server.telegrambotstarter.properties.TelegramBotProperties;
import ru.ilyxxxa.server.telegrambotstarter.strategy.AnswerStrategy;
import ru.ilyxxxa.server.telegrambotstarter.strategy.CallbackResponseStrategy;

import java.util.List;

@Configuration
@ConditionalOnProperty(value = "spring.telebot.enabled", havingValue = "true")
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotMainConfig {

    @Bean
    @ConditionalOnBean({AnswerStrategy.class, CallbackResponseStrategy.class})
    public BotStrategy botStrategy(List<AnswerStrategy> answerStrategies,
                                   List<CallbackResponseStrategy> callbackResponseStrategies) {
        return new BotStrategy(answerStrategies, callbackResponseStrategies);
    }

    @Bean
    @ConditionalOnBean(AnswerStrategy.class)
    @ConditionalOnMissingBean(CallbackResponseStrategy.class)
    public BotStrategy botStrategy(List<AnswerStrategy> answerStrategies) {
        return new BotStrategy(answerStrategies);
    }

    @Bean
    @ConditionalOnBean({BotStrategy.class, GetBotCommands.class})
    public TelegramBotExecutor telegramBotExecutorWithBotCommands(TelegramBotProperties telegramBotProperties,
                                                   BotStrategy botStrategy, GetBotCommands getBotCommands) {
        return new TelegramBotExecutor(telegramBotProperties, botStrategy, getBotCommands);
    }

    @Bean
    @ConditionalOnBean(BotStrategy.class)
    @ConditionalOnMissingBean(GetBotCommands.class)
    public TelegramBotExecutor telegramBotExecutor(TelegramBotProperties telegramBotProperties, BotStrategy botStrategy) {
        return new TelegramBotExecutor(telegramBotProperties, botStrategy);
    }

    @Bean
    @ConditionalOnBean(TelegramBotExecutor.class)
    public BotInitializer botInitializer(TelegramBotExecutor telegramBotExecutor) {
        return new BotInitializer(telegramBotExecutor);
    }
}
