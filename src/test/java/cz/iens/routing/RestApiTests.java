package cz.iens.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import cz.iens.routing.model.Country;
import cz.iens.routing.model.Routing;

@SpringBootTest
@AutoConfigureWebTestClient
class RestApiTests {


	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void testCountriesRest() {
		 webTestClient
	      .get()
	      .uri("/countries")
	      .accept(MediaType.APPLICATION_JSON)
	      .exchange()
	      .expectStatus()
	      .isOk()
	      .expectBodyList(Country.class)
	      .contains(new Country("CZE"));
	}

	@Test
	void testCountriesCodesRest() {
		 webTestClient
	      .get()
	      .uri("/countries/codes")
	      .accept(MediaType.APPLICATION_JSON)
	      .exchange()
	      .expectStatus()
	      .isOk()
	      .expectBody(Map.class)
	      .consumeWith(result -> {
				assertEquals("Czech Republic", result.getResponseBody().get("CZE"));
	      });
	}
	
	@Test
	void testRoutingRestCzeIta() {
		 webTestClient
	      .get()
	      .uri(uriBuilder -> uriBuilder
    		  	.path("/routing/{origin}/{destination}")
    		  	.build("CZE", "ITA"))
	      .accept(MediaType.APPLICATION_JSON)
	      .exchange()
	      .expectStatus()
	      .isOk()
	      .expectBody(Routing.class)
	      .isEqualTo(new Routing(List.of("CZE", "AUT", "ITA")));
	}
	
	@Test
	void testRoutingRestCzeUsa() {
		 webTestClient
	      .get()
	      .uri(uriBuilder -> uriBuilder
    		  	.path("/routing/{origin}/{destination}")
    		  	.build("CZE", "USA"))
	      .accept(MediaType.APPLICATION_JSON)
	      .exchange()
	      .expectStatus()
	      .isBadRequest();
	}
	
}
