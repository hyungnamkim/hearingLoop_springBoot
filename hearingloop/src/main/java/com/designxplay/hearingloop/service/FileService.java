package com.designxplay.hearingloop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<String> saveFiles(List<MultipartFile> files); // 파일 이름을 반환하는 타입으로 수정

}
