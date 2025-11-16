package com.farmermart.elasticsearch.repository.elastic;

import com.farmermart.elasticsearch.model.Farmer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerElasticRepository extends ElasticsearchRepository<Farmer, Long> {

    //@Query("{\"match_all\": {}}")
    //List<Farmer> findAll(); // or just extract IDs using stream


    //GET http://localhost:9200/farmers/_search?pretty
}
