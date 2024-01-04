package com.designxplay.hearingloop.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    private final Path fileStorageLocation = Paths.get("C:\\Users\\dxp_illustration\\Desktop\\testFileLocation");

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("C:\\Users\\dxp_illustration\\Desktop\\testFileLocation").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                // 파일이 존재하지 않거나 읽을 수 없는 경우
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            // 잘못된 URL 형식의 예외 처리
            // `Resource`의 구현체로 `ByteArrayResource`를 사용하여 문자열 메시지를 바디로 설정합니다.
            String message = "URL 형식이 잘못되었습니다: " + filename;
            ByteArrayResource resource = new ByteArrayResource(message.getBytes());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resource);
        }
    }

}
