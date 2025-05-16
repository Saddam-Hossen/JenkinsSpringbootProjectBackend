package com.example.userservice.repository;


import com.example.userservice.model.PabnaCategory;
import com.example.userservice.model.PabnaInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PabnaInformationRepository extends MongoRepository<PabnaInformation, String> {

}
