# Routing API example

In this example I will illustrate how to use Spring Boot WebFlux and JDK 11 for:
- download the countries.json data from the public Internet location
- calculation some route from the origin country to the destination country using land border crossings
- provide REST API to run calculation

### REST API

**Routing calculation**

	GET /routing/{origin}/{destination}

- origin and destination are required
- country codes use */countries/codes* path (see below)

Success response when route was successfully found:
      
	GET /routing/CZE/ITA 

	HTTP status 200
	{"route":["CZE","AUT","ITA"]}

Error response when no route was found:
      
	GET /routing/CZE/USA

	HTTP status 400
	{"route":[]}

**Country codes (names in english)**

	GET /countries/codes

**Minimalized countries data**

	GET /countries

### Requirements

* access to https://raw.githubusercontent.com/mledoze/countries/master/countries.json
* JDK 11
* Maven


### Run

build and tests

	mvn clean install

tests

	mvn test

run Spring Boot (port 8080) Windows users

	mvnw spring-boot:run

run Spring Boot (port 8080) Linux/macOS users

	chmod +x mvnw
	./mvnw spring-boot:run

**Warning: macOS users have to uncomment netty-resolver-dns-native-macos dependency in pom.xml, but still there are test fails when trying to download countries. But REST API is OK when run by mvnw**
