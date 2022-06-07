package ten.hoor.kafka.kafkatest;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ten.hoor.kafka.kafkatest.model.Book;
import ten.hoor.kafka.kafkatest.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class CassandraIntegrationTest {

    @Container
    public static final CassandraContainer cassandra
            = (CassandraContainer) new CassandraContainer("cassandra:4.0.4").withExposedPorts(9042);

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void setupCassandraConnectionProperties() {
        System.setProperty("spring.data.cassandra.keyspace-name", "mykeyspace");
        System.setProperty("spring.data.cassandra.contact-points", cassandra.getHost());
        System.setProperty("spring.data.cassandra.port", String.valueOf(cassandra.getMappedPort(9042)));
        createKeyspace(cassandra.getCluster());
    }

    static void createKeyspace(Cluster cluster) {
        try(Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS mykeyspace WITH replication = \n" +
                    "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

    @Test
    void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
        assertThat(cassandra.isRunning()).isTrue();
    }

    @Test
    public void test() {
        Book javaBook = new Book(
                Uuids.timeBased(), "Head First Java", "O'Reilly Media",
                ImmutableSet.of("Computer", "Software"));
        bookRepository.save(javaBook);
        System.out.println("Retrieving testbooks:");
        bookRepository.findAll().forEach(System.out::println);
    }
}
