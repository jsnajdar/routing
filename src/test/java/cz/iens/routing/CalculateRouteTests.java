package cz.iens.routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.iens.routing.service.CalculateRoute;

class CalculateRouteTests {
	
	Map<String, Set<String>> bordersMap;
	
	@BeforeEach
	void init() {
		bordersMap = new HashMap<>();
	}
	
	@Test
	void testSimple() {
		addCountry("CZE", "");
		List<String> route = getBorderCrossings("CZE", "CZE"); 
		assertNotNull(route);
		assertEquals(1, route.size());
		assertEquals("CZE", route.get(0));
	}
	
	@Test
	void testNeighbour() {
		addCountry("CZE", "SVK,AUT");
		addCountry("SVK", "CZE,HUN");
		List<String> route = getBorderCrossings("CZE", "SVK"); 
		assertNotNull(route);
		assertEquals(2, route.size());
		assertEquals("CZE", route.get(0));
		assertEquals("SVK", route.get(1));
	}
	
	@Test
	void testNotNeighbour() {
		addCountry("CZE", "SVK,AUT");
		addCountry("SVK", "CZE,HUN");
		addCountry("HUN", "SVK,AUT");
		List<String> route = getBorderCrossings("CZE", "HUN"); 
		assertNotNull(route);
		assertEquals(List.of("CZE", "SVK", "HUN"), route);
	}

	@Test
	void testNotNeighbourNextLevel() {
		addCountry("CZE", "SVK,AUT");
		addCountry("SVK", "CZE,HUN");
		addCountry("HUN", "SVK,AUT");
		addCountry("AUT", "SVK,CZE,SVN");
		addCountry("SVN", "AUT,ITA");
		addCountry("ITA", "SVN,FRA");
		List<String> route = getBorderCrossings("CZE", "ITA"); 
		assertNotNull(route);
		assertEquals(List.of("CZE", "AUT", "SVN", "ITA"), route);
	}

	@Test
	void testNonExisting() {
		addCountry("CZE", "SVK,AUT");
		addCountry("USA", "CAN");
		List<String> route = getBorderCrossings("CZE", "USA"); 
		assertNotNull(route);
		assertEquals(Collections.emptyList(), route);
	}

	
	
	private void addCountry(String cca3, String borders) {
		bordersMap.put(cca3, Set.of(borders.split(",")));
	}
	
	private List<String> getBorderCrossings(String origin, String destination) {
		CalculateRoute calculateRoute = new CalculateRoute(bordersMap);
		return calculateRoute.getBorderCrossings(origin, destination);
	}
}
