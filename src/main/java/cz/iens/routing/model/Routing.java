package cz.iens.routing.model;

import java.util.List;
import java.util.Objects;

public class Routing {

	private List<String> route;

	public Routing() {
		super();
	}
	
	public Routing(List<String> route) {
		super();
		this.route = route;
	}

	public List<String> getRoute() {
		return route;
	}

	public void setRoute(List<String> route) {
		this.route = route;
	}

	@Override
	public int hashCode() {
		return Objects.hash(route);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Routing other = (Routing) obj;
		return Objects.equals(route, other.route);
	}
	
}
