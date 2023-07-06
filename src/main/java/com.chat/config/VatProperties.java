package com.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "chat")
public class VatProperties {

  public Openai openai;

  @Data
  public static class Openai {
    String token;
  }
}
