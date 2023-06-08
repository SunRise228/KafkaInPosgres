package com.test.test.listener.kafkaListener;

import com.test.test.model.Message;
import com.test.test.repository.MessageRepository;
import com.test.test.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {
    @Value("${topic.name.consumer}")
    private String topicName;

    private final MessageRepository messageRepository;

    private final String topicKeyToFind = "__TypeId__";
    private final String topicValueToFind = "company";

    private boolean headerIsGood(Header header) {
        if(!topicKeyToFind.equals(header.key())) {
            return false;
        }
        return topicValueToFind.equals(new String(header.value()));
    }

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}", concurrency = "${scanner.concurrency:1}")
    public void consume(ConsumerRecord<String, String> payload){
        for (var headersIterator : payload.headers()) {
            if(!headerIsGood(headersIterator)) {
                continue;
            }
            Message message = JsonHelper.fromJson(payload.value(), Message.class);
            if (message != null) {
                messageRepository.save(message);
            }
        }
    }
}
