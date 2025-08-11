package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileController {

    private final Path filesLocation = Paths.get("C:/Users/PC_05/Desktop/db"); // 실제 파일 저장 폴더 경로로 변경하세요

    // 1) 파일 목록 반환
    @GetMapping("/files")
    public List<String> listFiles() throws IOException {
        if (!Files.exists(filesLocation) || !Files.isDirectory(filesLocation)) {
            throw new IOException("파일 저장 폴더가 존재하지 않습니다.");
        }
        try (var paths = Files.list(filesLocation)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        }
    }

    // 2) 파일 다운로드
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path file = filesLocation.resolve(filename).normalize();
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
