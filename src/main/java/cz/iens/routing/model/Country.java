package cz.iens.routing.model;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

	private String cca3;
	private Set<String> borders;
	private Name name; 
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Name {
		private String official;

		public String getOfficial() {
			return official;
		}

		public void setOfficial(String official) {
			this.official = official;
		}
	}
	
	public Country() {
		super();
	}
	
	public Country(String cca3) {
		super();
		this.cca3 = cca3;
	}

	public String getCca3() {
		return cca3;
	}

	public void setCca3(String cca3) {
		this.cca3 = cca3;
	}
	
	public Set<String> getBorders() {
		return borders;
	}

	public void setBorders(Set<String> borders) {
		this.borders = borders;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cca3);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return Objects.equals(cca3, other.cca3);
	}
	
}
