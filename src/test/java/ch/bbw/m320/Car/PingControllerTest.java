package ch.bbw.m320.Car;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class PingControllerTest implements WithAssertions {

	@Autowired
	private WebTestClient webClient;

	@Test
	void pingPong() {
		webClient.get()
				.uri("/api/ping")
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBody(String.class)
				.isEqualTo("pong");
	}

	@ParameterizedTest(strings =
			{}
	)
	void createPony_Happyflow_201(ponyDto) {
		webClient.post()
				.uri("/api/ponis")
				.bodyValue()
	}

}
