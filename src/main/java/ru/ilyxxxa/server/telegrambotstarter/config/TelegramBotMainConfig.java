package ru.ilyxxxa.server.telegrambotstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ilyxxxa.server.telegrambotstarter.components.BotInitializer;
import ru.ilyxxxa.server.telegrambotstarter.components.BotStrategy;
import ru.ilyxxxa.server.telegrambotstarter.components.TelegramBotExecutor;
import ru.ilyxxxa.server.telegrambotstarter.properties.TelegramBotProperties;
import ru.ilyxxxa.server.telegrambotstarter.strategy.AnswerStrategy;

import java.util.List;

@Configuration
@ConditionalOnProperty(value = "spring.telebot.enabled", havingValue = "true")
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotMainConfig {

    @Bean
    @ConditionalOnBean(AnswerStrategy.class)
    public BotStrategy botStrategy(List<AnswerStrategy> answerStrategies) {
        return new BotStrategy(answerStrategies);
    }

    @Bean
    @ConditionalOnBean(BotStrategy.class)
    public TelegramBotExecutor telegramBotExecutor(TelegramBotProperties telegramBotProperties, BotStrategy botStrategy) {
        return new TelegramBotExecutor(telegramBotProperties, botStrategy);
    }

    @Bean
    @ConditionalOnBean(TelegramBotExecutor.class)
    public BotInitializer botInitializer(TelegramBotExecutor telegramBotExecutor) {
        return new BotInitializer(telegramBotExecutor);
    }
}
