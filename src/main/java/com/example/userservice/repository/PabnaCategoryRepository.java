package com.example.userservice.repository;


import com.example.userservice.model.Pabna;
import com.example.userservice.model.PabnaCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PabnaCategoryRepository extends MongoRepository<PabnaCategory, String> {

}
