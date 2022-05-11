package cz.iens.routing.service;

import java.util.*;
import java.util.stream.Collectors;

public class CalculateRoute {

	private final Map<String, Set<String>>  countryBorders;
	
	public CalculateRoute(Map<String, Set<String>> countryBorders) {
		super();
		this.countryBorders = countryBorders;
	}
	
	public List<String> getBorderCrossings(String origin, String destination) {
		if (origin == null || destination == null) {
			return Collections.emptyList();
		}
		if (origin.equals(destination)) {
			return List.of(origin);
		}
		Set<String> visitedCountries = new HashSet<>();
		Queue<List<String>> routes = new LinkedList<>();
		routes.add(List.of(origin));

		while (!routes.isEmpty()) {
			List<String> inputRoute = routes.poll();
			String from = inputRoute.get(inputRoute.size() - 1);
			if (from.equals(destination)) {
				return inputRoute;
			}
			Set<String> nextCountry = countryBorders.get(from);
			if (nextCountry == null) {
				continue;
			}
			Set<List<String>> nextRoutes = nextCountry.parallelStream()
				.filter(country -> !visitedCountries.contains(country))
				.map(country -> {
					List<String> route = new LinkedList<>(inputRoute);
					route.add(country);
					return route;
				})
				.collect(Collectors.toSet());
			routes.addAll(nextRoutes);
			visitedCountries.addAll(nextCountry);
		}
		return Collections.emptyList();
	}
}
