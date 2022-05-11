package cz.iens.routing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import cz.iens.routing.client.CountriesClient;
import cz.iens.routing.exception.CountriesClientException;
import cz.iens.routing.model.Country;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class CountryClientTests {

	@Autowired
	private CountriesClient countriesClient;
	
	@Value("${countries.url}")
	private String countriesUrl;
	
	@Test
	void testCountriesClient() {
		ReflectionTestUtils.setField(countriesClient, "countriesUrl", countriesUrl);
		Flux<Country> countryFlux = countriesClient.getAllCountries()
				.filter(c->c.getCca3().equals("CZE"));
		
		 StepVerifier
         .create(countryFlux)
         .expectNext(new Country("CZE"))
         .expectComplete()
         .verify();
         
	}

	@Test
	void testCountriesClientBadUrl() {
		ReflectionTestUtils.setField(countriesClient, "countriesUrl", "https://raw.githubusercontent.com/mledoze/countries/master/blabla.json");
		Flux<Country> countryFlux = countriesClient.getAllCountries();
		 StepVerifier
         .create(countryFlux)
         .expectErrorMatches(throwable -> throwable instanceof CountriesClientException)
         .verify();
         
	}

	
	
}
