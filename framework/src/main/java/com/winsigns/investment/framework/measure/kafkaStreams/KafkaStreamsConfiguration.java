package com.winsigns.investment.framework.measure.kafkaStreams;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回kafkastream的相关配置
 * 
 * @author yimingjin
 * @since 0.0.2
 *
 */
@ConfigurationProperties(prefix = "kafka")
public class KafkaStreamsConfiguration {

  @Getter
  private final Stream stream = new Stream();

  @Getter
  private final Broker broker = new Broker();

  @Getter
  private final Zookeeper zookeeper = new Zookeeper();

  public static class Stream {
    @Getter
    @Setter
    String appid;
  }

  public static class Broker {
    @Getter
    @Setter
    String host;
  }

  public static class Zookeeper {
    @Getter
    @Setter
    String host;
  }


  /**
   * 
   * 创建一个为kafkastreams使用的配置bean
   * 
   * @author yimingjin
   *
   */
  @Configuration
  protected static class KafkaConfiguration {

    @Bean
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
      return new KafkaStreamsConfiguration();
    }
  }

  /**
   * 
   * 创建一个MeasureTopologyBuilder拓扑的Bean
   * 
   * @author yimingjin
   *
   */
  @Configuration
  protected static class BuilderConfiguration {
    @Bean
    MeasureTopologyBuilder measureTopologyBuilder(
        KafkaStreamsConfiguration kafkaStreamsConfiguration) {
      MeasureTopologyBuilder measureTopologyBuilder = new MeasureTopologyBuilder();
      return measureTopologyBuilder;
    }
  }
}
