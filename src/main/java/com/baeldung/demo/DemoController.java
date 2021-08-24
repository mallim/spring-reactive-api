package com.baeldung.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RestController
public class DemoController {

    @GetMapping(value="/foo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Foo> getFoo() {

        Random random = new Random();

        Supplier<Foo> fooSupplier = () -> Foo
                .builder()
                .id(Math.abs(random.nextLong()))
                .name(randomGenerateString())
                .build();

        return Flux
                .fromStream(Stream.generate( fooSupplier ) )
                .delayElements(Duration.ofSeconds(1))
                .repeat()
                .log();
    }

    private String randomGenerateString()
    {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
