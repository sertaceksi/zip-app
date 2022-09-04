package com.zip.initial.controller;

import com.zip.initial.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class ZipFileController {

    @Autowired
    ZipService zipService;

    @PostMapping(path = "/zip",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> zipFiles( MultipartFile[] files) throws IOException {
        return zipService.zipFiles(files);
    }


}
