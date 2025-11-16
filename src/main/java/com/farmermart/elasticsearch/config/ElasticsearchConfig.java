//package com.farmermart.elasticsearch.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Bean
//    public RestClient restClient() {
//        // Connect to local Elasticsearch
//        return RestClient.builder(
//                new HttpHost("localhost", 9200) // Change host/port if needed
//        ).build();
//    }
//
//    @Bean
//    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
//        RestClientTransport transport = new RestClientTransport(
//                restClient, new JacksonJsonpMapper()
//        );
//        return new ElasticsearchClient(transport);
//    }
//}
//package com.farmermart.elasticsearch.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.testcontainers.elasticsearch.ElasticsearchContainer;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Bean(destroyMethod = "stop")
//    public ElasticsearchContainer elasticsearchContainer() {
//        ElasticsearchContainer container = new ElasticsearchContainer(
//                "docker.elastic.co/elasticsearch/elasticsearch:8.18.2"
//        );
//        container.withEnv("discovery.type", "single-node");
//        container.start();
//        return container;
//    }
//
//    @Bean
//    public RestClient restClient(ElasticsearchContainer container) {
//        String host = container.getHttpHostAddress().replace("http://", "");
//        String[] split = host.split(":");
//        return RestClient.builder(
//                new HttpHost(split[0], Integer.parseInt(split[1]), "http")
//        ).build();
//    }
//
//    @Bean
//    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
//        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }
//}
package com.farmermart.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

        @Bean
        public RestClient restClient() {
            return RestClient.builder(new HttpHost("localhost", 9200)).build();
        }

        @Bean
        public ElasticsearchTransport transport(RestClient restClient) {
            return new RestClientTransport(restClient, new JacksonJsonpMapper());
        }

        @Bean
        public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
            return new ElasticsearchClient(transport);
        }
    }
