package com.test.test;

import com.test.test.model.Message;
import com.test.test.repository.MessageRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.UUID;

import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {TestApplication.class})
public class TestApplicationTests {

	@Autowired
	MessageRepository messageRepository;

	@Test
	public void embeddedDbTest() {

		Message addedMessage =  new Message(UUID.fromString("8a39ebbc-dc72-4848-b20e-5490a1ea0638"), "test3", "test3", "test3");

		messageRepository.save(addedMessage);

		for (Message repositoryIterrator : messageRepository.findAll()) {
			if(repositoryIterrator.getId().equals(addedMessage.getId())){
				Assert.isTrue(true, "Test passed");
				return;
			}
		}

		Assert.isTrue(false, "Test failed");
	}

}