# farmermart-elasticsearch

Spring Boot 3.5.7 Maven project with Spring Data Elasticsearch.
Java 23.

## Run
- Start Elasticsearch on localhost:9200
- Build: `mvn clean package`
- Run: `mvn spring-boot:run` or `java -jar target/farmermart-elasticsearch-0.0.1-SNAPSHOT.jar`

## APIs
- Products: `/api/products`
- Farmers: `/api/farmers`
- Example: `POST /api/products` with JSON body for product

## Notes
- Default Elasticsearch endpoint is `localhost:9200` configured in `application.yml`.
- Adjust mappings, analyzers, and index templates as needed for production.
