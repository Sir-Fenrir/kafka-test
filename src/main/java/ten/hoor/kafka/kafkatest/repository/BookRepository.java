package ten.hoor.kafka.kafkatest.repository;
import org.springframework.data.cassandra.repository.CassandraRepository;
import ten.hoor.kafka.kafkatest.model.Book;

import java.util.UUID;

public interface BookRepository extends CassandraRepository<Book, UUID> {
}
