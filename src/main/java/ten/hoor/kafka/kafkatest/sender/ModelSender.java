package ten.hoor.kafka.kafkatest.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ten.hoor.kafka.kafkatest.model.MessageOuterClass;

import java.util.Random;

@Component
public class ModelSender {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void act() throws InterruptedException {
        while(true) {
            sendMessage("Hello world as proto!");
            Thread.sleep(2000);
        }
    }

    public void sendMessage(String message) {
        MessageOuterClass.Message result = MessageOuterClass.Message.newBuilder()
                .setMessage(message)
                .setRandomNumber(new Random().nextInt(20))
                .build();

        ListenableFuture<SendResult<String, byte[]>> future =
                kafkaTemplate.send("bealdung", result.toByteArray());

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, byte[]> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }

}
