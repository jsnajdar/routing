package cz.iens.routing;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cz.iens.routing.client.CountriesClient;
import cz.iens.routing.model.Routing;
import cz.iens.routing.service.RoutingService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class CalculateRouteServiceTests {
	
	@Autowired
	private CountriesClient countriesClient;
	
	@Autowired
	private RoutingService calculateRouteService;
	
	@Test
	void testCalculateRouteServiceCzeIta() {
		Mono<Routing> routeMono = calculateRouteService.getBorderCrossings(
				countriesClient.getAllCountries(), 
				"CZE", 
				"ITA");
		StepVerifier
         .create(routeMono)
         .expectNext(new Routing(List.of("CZE", "AUT", "ITA")))
         .expectComplete()
         .verify();
	}
	
	@Test
	void testCalculateRouteServiceCzeHun() {
		Mono<Routing> routeMono = calculateRouteService.getBorderCrossings(
				countriesClient.getAllCountries(), 
				"CZE", 
				"HUN");
		StepVerifier
         .create(routeMono)
         .expectNext(new Routing(List.of("CZE", "AUT", "HUN")))
         .expectComplete()
         .verify();
	}
	
	@Test
	void testCalculateRouteServiceCzeSpa() {
		Mono<Routing> routeMono = calculateRouteService.getBorderCrossings(
				countriesClient.getAllCountries(), 
				"CZE", 
				"ESP");
		StepVerifier
         .create(routeMono)
         .expectNext(new Routing(List.of("CZE", "DEU", "FRA", "ESP")))
         .expectComplete()
         .verify();
	}
	
	@Test
	void testCalculateRouteServiceCzeUsa() {
		Mono<Routing> routeMono = calculateRouteService.getBorderCrossings(
				countriesClient.getAllCountries(), 
				"CZE", 
				"USA");
		StepVerifier
         .create(routeMono)
         .expectNext(new Routing(Collections.emptyList()))
         .expectComplete()
         .verify();
	}
	
}
