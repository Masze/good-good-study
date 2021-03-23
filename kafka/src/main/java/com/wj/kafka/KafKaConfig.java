package com.wj.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
    public  class KafKaConfig {

        private final KafkaTemplate kafkaTemplate;

        @Autowired
        public KafKaConfig(KafkaTemplate kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
        }

    }
