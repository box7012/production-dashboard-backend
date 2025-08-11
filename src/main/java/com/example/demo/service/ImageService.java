package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;

@Service
public class ImageService {

    // 예: 이미지 루트 폴더 경로 (환경변수나 application.yml에서 주입 권장)
    private final Path rootLocation = Paths.get("C:/image_storage");

    // 해당 tab 폴더 안의 이미지 파일명 리스트 반환
    public List<String> listImageFilenames(String tab) {
        try {
            Path dir = rootLocation.resolve(tab);
            if (!Files.exists(dir) || !Files.isDirectory(dir)) {
                return Collections.emptyList();
            }
            try (Stream<Path> stream = Files.list(dir)) {
                return stream
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to list images for tab: " + tab, e);
        }
    }

    // 이미지 파일을 Resource로 로드
    public Resource loadImageAsResource(String tab, String filename) {
        try {
            Path file = rootLocation.resolve(tab).resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL for file: " + filename, e);
        }
    }
}