package com.farmermart.elasticsearch.repository.jpa;

import com.farmermart.elasticsearch.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    //List<Farmer> findByNameContainingIgnoreCase(String name);

    @Query("SELECT f.id FROM Farmer f")
    List<Long> findAllIds();
}


