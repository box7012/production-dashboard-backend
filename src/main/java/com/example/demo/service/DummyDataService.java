package com.example.demo.service;

import com.example.demo.model.DummyData;
import com.example.demo.repository.DummyDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyDataService {
    private final DummyDataRepository repository;

    public DummyDataService(DummyDataRepository repository) {
        this.repository = repository;
    }

    public List<DummyData> getAllData() {
        return repository.findAll();
    }

    public List<DummyData> getDataAfterCDate(String cdate) {
        return repository.findByCDateAfter(cdate);
    }
}