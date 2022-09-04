package com.zip.initial.service;

import com.zip.initial.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class ZipService {

    public ResponseEntity<byte[]> zipFiles(MultipartFile[] fileArray) throws IOException {
        if (fileArray == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constant.FILE_LIST_EMPTY.getBytes(StandardCharsets.UTF_8));
        }
        log.debug("ZipFiles method started. fileArray size : {}", fileArray.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream out = new ZipOutputStream(bos)) {
            for (MultipartFile file : fileArray) {
                ZipEntry entry = new ZipEntry(Objects.requireNonNull(file.getOriginalFilename()));
                out.putNextEntry(entry);
                log.debug("File {} is added to zip", file.getOriginalFilename());
                byte[] data = file.getBytes();
                out.write(data, 0, data.length);
            }
        } catch (IOException e) {
            log.error("An error occurred during zipping the files. Error message : {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(bos.toByteArray());
    }
}
