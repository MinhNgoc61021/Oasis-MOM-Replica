package app.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class PublisherConfig {

    @Value("${jms.broker.url}")
    private String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory publisherActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(
                publisherActiveMQConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate =
                new JmsTemplate(cachingConnectionFactory());
        jmsTemplate.setPubSubDomain(true);

        return jmsTemplate;
    }

    @Bean(name="firstTopicListener")
    public DefaultJmsListenerContainerFactory firstTopicListenerFactory(@Qualifier("publisherActiveMQConnectionFactory") ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean(name="firstQueueListener")
    public DefaultJmsListenerContainerFactory firstQueueTopicListenerFactory(@Qualifier("publisherActiveMQConnectionFactory")ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}