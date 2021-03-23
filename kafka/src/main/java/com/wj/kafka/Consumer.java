package com.wj.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
    public class Consumer {

        @KafkaListener(topics = "someTopic")
        public void processMessage(String content) {
            System.out.println("2:" + content);
        }
    }
