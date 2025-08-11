package com.example.demo.controller;

import com.example.demo.model.DummyData;
import com.example.demo.service.DummyDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dummy")
public class DummyDataController {
    private final DummyDataService service;

    public DummyDataController(DummyDataService service) {
        this.service = service;
    }

    @GetMapping
    public List<DummyData> getDummyData() {
        return service.getAllData();
    }

    @GetMapping("/after")
    public List<DummyData> getDataAfter(@RequestParam String cdate) {
        return service.getDataAfterCDate(cdate);
    }
}