package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.ImageService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    // 생성자 주입
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // 1) 이미지 파일명 리스트 반환
    @GetMapping("/{tab}")
    public ResponseEntity<List<String>> listImages(@PathVariable String tab) {
        List<String> images = imageService.listImageFilenames(tab);
        return ResponseEntity.ok(images);
    }

    // 2) 실제 이미지 파일 스트리밍
    @GetMapping("/serve/{tab}/{filename:.+}")
    public ResponseEntity<Resource> serveImage(
            @PathVariable String tab,
            @PathVariable String filename) {

        Resource file = imageService.loadImageAsResource(tab, filename);

        // 이미지 컨텐츠 타입은 필요에 따라 동적으로 변경 가능
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG) // 기본 jpg, png 등 다양하면 확장자에 따라 바꿀 수도 있음
                .body(file);
    }

    @PostMapping("/{tab}/prepend-tag")
    public ResponseEntity<?> prependTagToAllImages(
            @PathVariable String tab,
            @RequestBody Map<String, String> body) {

        String tag = body.get("tag");
        if (tag == null || tag.isEmpty()) {
            return ResponseEntity.badRequest().body("Tag is required");
        }

        try {
            imageService.prependTagToAllImages(tab, tag);
            return ResponseEntity.ok("Tag prepended successfully to all images");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to prepend tag: " + e.getMessage());
        }
    }

    @PostMapping("/{tab}/remove-tag")
    public ResponseEntity<Void> removeTagFromAllImages(@PathVariable String tab) {
        imageService.removeTagFromAllImages(tab);
        return ResponseEntity.ok().build();
    }

}
