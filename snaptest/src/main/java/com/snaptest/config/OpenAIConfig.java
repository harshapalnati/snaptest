package com.snaptest.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig
{
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public String openAPiKey()
    {
        return dotenv.get("OPENAI_API_KEY");
    }
}
