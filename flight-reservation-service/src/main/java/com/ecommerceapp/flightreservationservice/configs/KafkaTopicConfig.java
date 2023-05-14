package com.ecommerceapp.flightreservationservice.configs;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${KAFKA_ADDRESS}")
    private String bootstrapAddress;

    @Value(value = "${KAFKA_TOPIC_1}")
    private String kafkaTopic1;

    @Value(value = "${KAFKA_TOPIC_2}")
    private String kafkaTopic2;

    @Value(value = "${KAFKA_TOPIC_3}")
    private String kafkaTopic3;

    @Value(value = "${KAFKA_TOPIC_4}")
    private String kafkaTopic4;

    /*@Value(value = "${KAFKA_TOPIC_7}")
    private String kafkaTopic7;

    @Value(value = "${KAFKA_TOPIC_8}")
    private String kafkaTopic8;*/

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(kafkaTopic1, 100, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(kafkaTopic2, 100, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic(kafkaTopic3, 100, (short) 1);
    }

    @Bean
    public NewTopic topic4() {
        return new NewTopic(kafkaTopic4, 100, (short) 1);
    }

    /*@Bean
    public NewTopic topic7() {
        return new NewTopic(kafkaTopic7, 100, (short) 1);
    }

    @Bean
    public NewTopic topic8() {
        return new NewTopic(kafkaTopic8, 100, (short) 1);
    }*/

}