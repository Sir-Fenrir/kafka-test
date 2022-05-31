//package ten.hoor.kafka.kafkatest.sender;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//
//@Component
//public class TextSender {
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void act() throws InterruptedException {
//        while(true) {
//            sendMessage("Hello world!");
//            Thread.sleep(5000);
//        }
//    }
//
//    public void sendMessage(String message) {
//
//        ListenableFuture<SendResult<String, String>> future =
//                kafkaTemplate.send("bealdung", message);
//
//        future.addCallback(new ListenableFutureCallback<>() {
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                System.out.println("Sent message=[" + message +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("Unable to send message=["
//                        + message + "] due to : " + ex.getMessage());
//            }
//        });
//    }
//
//}
