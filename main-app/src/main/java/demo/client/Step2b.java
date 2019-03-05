package demo.client;

import demo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Step2b {

    private static final Logger logger = LoggerFactory.getLogger(Step2b.class);

    private static WebClient client = WebClient.create("http://localhost:8081?delay=2");


    public static void main(String[] args) {

        Instant start = Instant.now();

        List<Mono<Person>> personMonos = Stream.of(1, 2, 3)
                .map(anInt -> client.get().uri("/person/{id}", anInt).retrieve().bodyToMono(Person.class))
                .collect(Collectors.toList());

        Mono.when(personMonos).block();

        logTime(start);
    }


    private static void logTime(Instant start) {
        logger.debug("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() + "ms");
    }

}
