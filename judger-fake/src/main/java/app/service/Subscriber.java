package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.util.Map;


@Component
public class Subscriber {
    private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

    
    @JmsListener(destination="Consumer.A.VirtualTopic.Sharingan",containerFactory = "firstQueueListener")
    @Async
    public void receiveFromSharigan(Map<String, String> Message) throws JMSException {
        logger.debug("Sharingan Message ===== "+ Message.get("msg"));
        System.out.println("Sharingan Message ===== "+ Message.get("msg"));
        try{
            Thread.sleep(500L);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

//    @JmsListener(destination="Consumer.A.VirtualTopic.Topic1",containerFactory = "firstQueueListener")
//    @Async
//    public void receiveVTopicA(Map<String, Object> Message)throws JMSException {
//        logger.debug("VTopic A ===== "+ Message.get("msg"));
//        System.out.println("VTopic A ======="+ Message.get("msg"));
//        try{
//            Thread.sleep(500L);
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//    }
}