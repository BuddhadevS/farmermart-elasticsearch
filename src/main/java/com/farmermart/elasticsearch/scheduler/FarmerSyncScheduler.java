package com.farmermart.elasticsearch.scheduler;

import com.farmermart.elasticsearch.model.Farmer;
import com.farmermart.elasticsearch.repository.elastic.FarmerElasticRepository;
import com.farmermart.elasticsearch.repository.jpa.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class FarmerSyncScheduler {

    private final FarmerRepository jpaRepository;
    private final FarmerElasticRepository elasticRepository;

    @Scheduled(fixedRate = 60000) // every 60 seconds
    public void syncFarmers() {
        System.out.println("🔄 Running sync between MySQL and Elasticsearch...");

        // Step 1: Fetch all MySQL IDs
        List<Long> mysqlIds = jpaRepository.findAll()
                .stream()
                .map(farmer -> farmer.getId())
                .collect(Collectors.toList());

        // Step 2: Fetch all Elasticsearch IDs
        List<Long> esIds = StreamSupport.stream(elasticRepository.findAll().spliterator(), false)
                .map(Farmer::getId)
                .collect(Collectors.toList());


        // Step 3: Remove from Elasticsearch if not present in MySQL
        esIds.stream()
                .filter(id -> !mysqlIds.contains(id))
                .forEach(id -> {
                    elasticRepository.deleteById(id);
                    System.out.println("🗑 Deleted orphan record from Elasticsearch: " + id);
                });

        System.out.println("✅ Sync completed between MySQL and Elasticsearch.");
    }
}
