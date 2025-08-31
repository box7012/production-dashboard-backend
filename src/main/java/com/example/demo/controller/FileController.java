package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api")
public class FileController {

    private final Path baseFilesLocation = Paths.get("C:/Users/PC_05/Desktop/DataPool");

    // 폴더별 파일 목록 반환
    @GetMapping("/files/{folderName}")
    public List<String> listFilesByFolder(@PathVariable String folderName) throws IOException {
        Path folderPath = baseFilesLocation.resolve(folderName).normalize();

        if (!Files.exists(folderPath) || !Files.isDirectory(folderPath)) {
            throw new IOException("폴더가 존재하지 않습니다: " + folderPath.toString());
        }

        try (Stream<Path> paths = Files.list(folderPath)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.toLowerCase().endsWith(".zip"))
                    .collect(Collectors.toList());
        }
    }

    // 전체 현황 - 라인별로 나눠서 반환
    @GetMapping("/files/all")
    public Map<String, List<String>> listAllZipFilesByFolder() throws IOException {
        String[] folders = {"D02", "D07", "D14", "D20"};
        Map<String, List<String>> result = new LinkedHashMap<>();

        for (String folder : folders) {
            Path folderPath = baseFilesLocation.resolve(folder).normalize();

            if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
                try (Stream<Path> paths = Files.list(folderPath)) {
                    List<String> zipFiles = paths
                            .filter(Files::isRegularFile)
                            .map(Path::getFileName)
                            .map(Path::toString)
                            .filter(name -> name.toLowerCase().endsWith(".zip"))
                            .collect(Collectors.toList());
                    result.put(folder, zipFiles);
                }
            } else {
                result.put(folder, new ArrayList<>()); // 폴더 없으면 빈 리스트
            }
        }

        return result;
    }

    // 파일 다운로드 - 폴더명과 파일명 받아서 다운로드 처리
    @GetMapping("/download/{folderName}/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String folderName,
            @PathVariable String filename) {
        try {
            Path file = baseFilesLocation.resolve(folderName).resolve(filename).normalize();
            if (!Files.exists(file) || !Files.isRegularFile(file)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(file.toUri());
            String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
