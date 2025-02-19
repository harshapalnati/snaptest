package com.snaptest.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SnapTest API")
                        .version("1.0")
                        .description("API documentation for SnapTest - AI-generated test case generator"));
    }
}
