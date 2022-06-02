package ten.hoor.kafka.kafkatest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import ten.hoor.kafka.kafkatest.listener.ModelListener;
import ten.hoor.kafka.kafkatest.sender.ModelSender;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedKafkaIntegrationTest {

    @Autowired
    private ModelListener listener;

    @Autowired
    private ModelSender producer;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingtoSimpleProducer_thenMessageReceived()
            throws Exception {
        producer.sendMessage("Hello world!");
        Thread.sleep(2000);
    }
}
