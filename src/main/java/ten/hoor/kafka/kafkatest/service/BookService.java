package ten.hoor.kafka.kafkatest.service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableSet;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ten.hoor.kafka.kafkatest.model.Book;
import ten.hoor.kafka.kafkatest.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void act() throws InterruptedException {
        Book javaBook = new Book(
                Uuids.timeBased(), "Head First Java", "O'Reilly Media",
                ImmutableSet.of("Computer", "Software"));
        bookRepository.save(javaBook);
        System.out.println("Retrieving books:");
        bookRepository.findAll().forEach(System.out::println);
    }

}
