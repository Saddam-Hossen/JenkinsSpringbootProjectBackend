package com.example.userservice.repository;

import com.example.userservice.model.QuizAttendance;
import com.example.userservice.model.QuizFeedback;
import com.example.userservice.model.QuizResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface QuizResultRepository extends MongoRepository<QuizResult, String> {
    List<QuizResult> findByIdNumber(String studentIdNumber);
    void deleteByClassName(String className);
    Optional<QuizResult> findByIdNumberAndClassNameAndClassNumber(String studentIdNumber, String className, String classNumber);

}

