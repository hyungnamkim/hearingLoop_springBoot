package com.designxplay.hearingloop.service;

import com.designxplay.hearingloop.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private static final String DIRECTORY = "C:\\Users\\dxp_illustration\\Desktop\\testFileLocation\\";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    public List<String> saveFiles(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        if (files == null || files.isEmpty()) {
            System.out.println("업로드된 파일이 없습니다.");
            return fileNames;
        }

        for (MultipartFile file : files) {
            try {
                String originalFileName = file.getOriginalFilename();
                String extension = "";

                // 확장자 추출
                int lastDot = originalFileName.lastIndexOf(".");
                if (lastDot > 0) {
                    extension = originalFileName.substring(lastDot);
                }

                // 파일 이름을 현재 시간으로 포맷팅하여 생성
                String timestamp = LocalDateTime.now().format(FORMATTER);
                String fileName = timestamp + extension;

                // 저장 경로 생성
                Path path = Paths.get(DIRECTORY + fileName);

                // 디렉토리 생성
                File dir = new File(DIRECTORY);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 파일 저장
                byte[] bytes = file.getBytes();
                Files.write(path, bytes);

                System.out.println("파일 저장 성공: " + path);
                fileNames.add(fileName); // 저장된 파일 이름을 리스트에 추가

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileNames; // 파일 이름 리스트 반환
    }
}
