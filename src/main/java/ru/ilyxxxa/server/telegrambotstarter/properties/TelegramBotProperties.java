package ru.ilyxxxa.server.telegrambotstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.telebot")
public class TelegramBotProperties {

    private String token;

    private String username;

    private String host;

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
