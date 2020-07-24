package app.service;

import com.google.gson.Gson;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Publisher {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping(value = "/sendToJudger", method = RequestMethod.GET)
    public void sendMail() {
        Map<String, Object> mapMessage = new HashMap<>();
        for(int i = 0; i < 10; i++){
            mapMessage.put("msg", "J " + i);
            ActiveMQTopic topic = new ActiveMQTopic("VirtualTopic.Sharingan");
            this.jmsMessagingTemplate.convertAndSend(topic, mapMessage);
        }
    }
}
