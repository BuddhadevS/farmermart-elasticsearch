package com.farmermart.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.farmermart.elasticsearch.repository.jpa")
@EnableElasticsearchRepositories(basePackages = "com.farmermart.elasticsearch.repository.elastic")
@EnableScheduling
public class FarmerMartElasticsearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(FarmerMartElasticsearchApplication.class, args);
    }
}

