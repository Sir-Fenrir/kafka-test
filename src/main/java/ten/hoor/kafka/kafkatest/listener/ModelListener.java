package ten.hoor.kafka.kafkatest.listener;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ten.hoor.kafka.kafkatest.model.MessageOuterClass;

@Component
public class ModelListener {

    @KafkaListener(topics = "bealdung", groupId = "foo")
    public void listenGroupFoo(byte[] message) throws InvalidProtocolBufferException {
        MessageOuterClass.Message result = MessageOuterClass.Message.parseFrom(message);
        System.out.println("Received Message in group foo: " + result.getMessage() + " with random number: " + result.getRandomNumber());
    }

}
