package cz.iens.routing.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.iens.routing.exception.CountriesClientException;
import cz.iens.routing.model.Country;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CountriesClient {

	@Value("${countries.url}")
	private String countriesUrl;
	
	public Flux<Country> getAllCountries() {		
		return WebClient.builder()
		.baseUrl(countriesUrl)
		.exchangeStrategies(
			ExchangeStrategies.builder()
				.codecs(configurer -> 
						configurer.defaultCodecs().jackson2JsonDecoder(
							new Jackson2JsonDecoder(new ObjectMapper(), MediaType.TEXT_PLAIN)
						)
				).build()
		).build()
		.get()
		.retrieve()
		.onStatus(HttpStatus::isError, response -> 
			Mono.error(new CountriesClientException("cannot load countries", countriesUrl, response.statusCode()))
		)
		.bodyToFlux(Country.class);
	}
	
}
