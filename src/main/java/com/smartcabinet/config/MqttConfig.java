package com.smartcabinet.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * MQTT配置类
 * 
 * @author SmartCabinet Team
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "mqtt.enabled", havingValue = "true", matchIfMissing = false)
public class MqttConfig {
    
    @Value("${mqtt.broker.url}")
    private String brokerUrl;
    
    @Value("${mqtt.broker.username}")
    private String username;
    
    @Value("${mqtt.broker.password}")
    private String password;
    
    @Value("${mqtt.broker.client-id}")
    private String clientId;
    
    @Value("${mqtt.broker.keep-alive}")
    private Integer keepAlive;
    
    @Value("${mqtt.broker.clean-session}")
    private Boolean cleanSession;
    
    @Value("${mqtt.topics.device-status}")
    private String deviceStatusTopic;
    
    @Value("${mqtt.topics.device-sensor}")
    private String deviceSensorTopic;
    
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        
        options.setServerURIs(new String[]{brokerUrl});
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setKeepAliveInterval(keepAlive);
        options.setCleanSession(cleanSession);
        options.setConnectionTimeout(30);
        
        factory.setConnectionOptions(options);
        return factory;
    }
    
    // MQTT入站通道
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }
    
    // MQTT出站通道
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
    
    // MQTT消息生产者（接收消息）
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId + "_inbound", mqttClientFactory(),
                        deviceStatusTopic, deviceSensorTopic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }
    
    // MQTT消息处理器（发送消息）
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(clientId + "_outbound", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("device/+/command");
        messageHandler.setDefaultQos(1);
        return messageHandler;
    }
}
