package com.example.rsocket;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Controller
public class RsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsocketApplication.class, args);
	}

	@MessageMapping("request-response")
	Mono<Message> requestResponse(final Message message) {
		System.out.println("Received request-response message: {}"+message);
		return Mono.just(new Message("You said: " + message.getMessage()));
	}

	@MessageMapping("request-stream")
	Flux<Message> stream(final Message message) {
		return Flux
				// create a new indexed Flux emitting one element every second
				.interval(Duration.ofSeconds(1))
				// create a Flux of new Messages using the indexed Flux
				.map(index -> new Message("You said: " + message.getMessage() + ". Response #" + index))
				// show what's happening
				.log();
	}
}
