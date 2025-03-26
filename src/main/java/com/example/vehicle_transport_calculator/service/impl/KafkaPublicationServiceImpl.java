package com.example.vehicle_transport_calculator.service.impl;

import com.example.vehicle_transport_calculator.model.dto.ExRateDTO;
import com.example.vehicle_transport_calculator.service.KafkaPublicationService;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublicationServiceImpl implements KafkaPublicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPublicationServiceImpl.class);
    private final KafkaTemplate<String, ExRateDTO> kafkaTemplate;

    public KafkaPublicationServiceImpl(KafkaTemplate<String, ExRateDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishExRate(ExRateDTO exRateDTO) {
        kafkaTemplate.
                send(EX_RATES_TOPIC, exRateDTO.currency(), exRateDTO).
                whenComplete(
                        (res, ex) -> {
                            if (ex == null) {
                                RecordMetadata recordMetadata = res.getRecordMetadata();
                                LOGGER.info(
                                        "Successfully send key {} to topic/partiotion/offset={}/{}/{}.",
                                        exRateDTO.currency(),
                                        recordMetadata.topic(),
                                        recordMetadata.partition(),
                                        recordMetadata.offset()
                                );
                            } else {
                                LOGGER.error("Error producing message in kafka with key {}.",
                                        exRateDTO.currency(),
                                        ex);
                            }
                        }
                );


    }
}
