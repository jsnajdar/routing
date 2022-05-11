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

* JDK 11
* Maven


### Run

build and tests

	mvn clean install

tests

	mvn test

run Spring Boot (port 8080)

	mvnw spring-boot:run


- macOS AArch64 users have to uncomment netty-resolver-dns-native-macos dependency in pom.xml
