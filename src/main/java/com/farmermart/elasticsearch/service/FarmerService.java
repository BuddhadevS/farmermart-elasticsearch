package com.farmermart.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhrasePrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.WildcardQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.farmermart.elasticsearch.model.Farmer;
import com.farmermart.elasticsearch.repository.elastic.FarmerElasticRepository;
import com.farmermart.elasticsearch.repository.jpa.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository jpaRepository;
    private final FarmerElasticRepository elasticRepository;
    private final ElasticsearchClient elasticsearchClient;  // ✅ modern client

    private static final String INDEX = "farmers";

    // ✅ Save to both MySQL and Elasticsearch
    public Farmer saveFarmer(Farmer farmer) {
        Farmer saved = jpaRepository.save(farmer);
        elasticRepository.save(saved);
        return saved;
    }

    public List<Farmer> searchFarmerByName(String name) {
        try {
            // Build combined boolean query
            Query query = BoolQuery.of(b -> b
                    .should(MatchPhraseQuery.of(m -> m.field("name").query(name))._toQuery())
                    .should(MatchPhrasePrefixQuery.of(m -> m.field("name").query(name))._toQuery())
                    .should(WildcardQuery.of(m -> m.field("name").wildcard("*" + name.toLowerCase() + "*"))._toQuery())
            )._toQuery();

            // Build and execute search request
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index(INDEX)
                    .size(20)
                    .query(query)
            );

            SearchResponse<Farmer> response = elasticsearchClient.search(searchRequest, Farmer.class);

            // Convert results to list
            List<Farmer> farmers = new ArrayList<>();
            response.hits().hits().forEach(hit -> farmers.add(hit.source()));

            return farmers;

        } catch (IOException e) {
            throw new RuntimeException("Error searching farmers in Elasticsearch", e);
        }
    }

    // ✅ Get all farmers from MySQL
    public List<Farmer> getAllFarmers() {
        return jpaRepository.findAll();
    }

    public void deleteFarmer(Long id) {
        jpaRepository.deleteById(id);
        try {
            elasticsearchClient.delete(d -> d.index(INDEX).id(String.valueOf(id)));
        } catch (IOException e) {
            throw new RuntimeException("Error deleting farmer from Elasticsearch", e);
        }
    }
}
