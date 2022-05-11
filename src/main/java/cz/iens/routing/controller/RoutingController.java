package cz.iens.routing.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import cz.iens.routing.client.CountriesClient;
import cz.iens.routing.exception.CountriesClientException;
import cz.iens.routing.model.Country;
import cz.iens.routing.model.Routing;
import cz.iens.routing.service.RoutingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RoutingController {

	@Autowired
	CountriesClient countriesClient;
	
	@Autowired
	RoutingService calculateRouteService;
	
	@GetMapping("/countries")
	public Flux<Country> countries() {
		return countriesClient.getAllCountries();
	}
	
	@GetMapping("/countries/codes")
	public Mono<Map<String, String>> countriesCodes() {
		return countriesClient.getAllCountries().collectMap(Country::getCca3, country->country.getName().getOfficial());
	}
	
	@GetMapping("/routing/{origin}/{destination}")
	public Mono<Routing> routing(@PathVariable String origin, @PathVariable String destination, ServerHttpResponse response) {
		return calculateRouteService.getBorderCrossings(countriesClient.getAllCountries(), origin, destination)
			.map(routing -> {
				if (routing == null || routing.getRoute() == null || routing.getRoute().isEmpty()) {
					response.setStatusCode(HttpStatus.BAD_REQUEST);
				}
				return routing;
			});
	}
	
	
	@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "cannot load countries")
	@ExceptionHandler(CountriesClientException.class)
	public void CountriesClientExceptionHandler() {
		//
	}
	
}
