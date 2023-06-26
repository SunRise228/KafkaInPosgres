package com.test.test.listener.kafkaListener;

import com.test.test.model.Company;
import com.test.test.repository.CompanyRepository;
import com.test.test.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {
    @Value("${topic.name.consumer}")
    private String topicName;

    private final CompanyRepository messageRepository;

    private boolean headerIsGood(Header header) {
        String TOPIC_KEY_TO_FIND = "__TypeId__";
        String TOPIC_VALUE_TO_FIND = "company";

        if(!TOPIC_KEY_TO_FIND.equals(header.key())) {
            return false;
        }
        return TOPIC_VALUE_TO_FIND.equals(new String(header.value()));
    }

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}", concurrency = "${scanner.concurrency:1}")
    public void consume(ConsumerRecord<String, String> payload){
        for (var headersIterator : payload.headers()) {
            if(!headerIsGood(headersIterator)) {
                continue;
            }
            Company company = JsonHelper.fromJson(payload.value(), Company.class);
            company.setCreateTime(Instant.now());
            company.setLastUpdateTime(Instant.now());
            messageRepository.save(company);
        }
    }


}
