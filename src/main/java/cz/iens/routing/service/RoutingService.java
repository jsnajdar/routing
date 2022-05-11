package cz.iens.routing.service;

import org.springframework.stereotype.Service;

import cz.iens.routing.model.Country;
import cz.iens.routing.model.Routing;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoutingService {

	public Mono<Routing> getBorderCrossings(Flux<Country> countries, String origin, String destination) {
		return countries
		.collectMap(Country::getCca3, Country::getBorders)
		.map(bordersMap -> {
			CalculateRoute calc = new CalculateRoute(bordersMap);
			return calc.getBorderCrossings(origin, destination);
		})
		.map(Routing::new);
	}
}
