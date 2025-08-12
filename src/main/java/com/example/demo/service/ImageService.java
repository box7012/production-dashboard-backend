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

    private final Path rootLocation = Paths.get("C:/Users/PC_05/Desktop/ImagePool");

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

    public void prependTagToAllImages(String tab, String tag) {
    try {
        Path dir = rootLocation.resolve(tab);
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            throw new RuntimeException("Tab directory does not exist: " + tab);
        }

        // 폴더 내 모든 파일 리스트 가져오기
        try (Stream<Path> stream = Files.list(dir)) {
            stream.filter(Files::isRegularFile).forEach(path -> {
                String filename = path.getFileName().toString();

                // 이미 태그가 붙어있는 경우 중복 안 붙이도록 처리 (선택사항)
                if (!filename.startsWith(tag + "_")) {
                    Path target = path.resolveSibling(tag + "_" + filename);
                    try {
                        Files.move(path, target);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to rename file: " + filename, e);
                    }
                }
            });
        }
    } catch (IOException e) {
        throw new RuntimeException("Failed to prepend tag to images in tab: " + tab, e);
    }
}

}