package com.example.demo.repository;

import com.example.demo.model.DummyData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DummyDataRepository extends MongoRepository<DummyData, String> {

    // CDate가 특정 값 이후인 데이터만
    @Query("{ 'CDate': { $gt: ?0 } }")
    List<DummyData> findByCDateAfter(String cdate);
}