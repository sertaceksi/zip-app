package com.zip.initial;

import com.zip.initial.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ZipFileControllerIntegrationTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int port;

    String url;

    HttpHeaders headers;

    @BeforeEach
    void init() {
        url = "http://localhost:" + port + "/zip";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    }

    @Test
    void integrationTestsZipFiles() {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (int i = 0; i < 10; i++) {
            String fileName = "file" + i;
            body.add(fileName, getHttpEntity(fileName));
        }
        HttpEntity<MultiValueMap<String, Object>> requestOKEntity = new HttpEntity<>(body, headers);
        ResponseEntity<byte[]> response = testRestTemplate.postForEntity(url, requestOKEntity, byte[].class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void integrationTestsZipFilesListEmpty() {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (int i = 0; i < 4; i++) {
            String fileName = "file" + i;
            body.add(fileName, getListEmptyHttpEntity(fileName));
        }
        HttpEntity<MultiValueMap<String, Object>> requestListEmptyEntity = new HttpEntity<>(body, headers);
        ResponseEntity<byte[]> response = testRestTemplate.postForEntity(url, requestListEmptyEntity, byte[].class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "File array is null.");
        Assertions.assertEquals(Constant.FILE_LIST_EMPTY, new String(Objects.requireNonNull(response.getBody())), "Message string doesn't match.");
    }

    @Test
    void integrationTestsZipFilesInternalServerError() {
        HttpEntity<MultiValueMap<String, Object>> requestInternalServerErrorEntity = new HttpEntity<>(null, headers);
        ResponseEntity<byte[]> response = testRestTemplate.postForEntity(url, requestInternalServerErrorEntity, byte[].class);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private HttpEntity<byte[]> getHttpEntity(String fileName) {
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("files")
                .filename(fileName)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        return new HttpEntity<>("Hello World".getBytes(), fileMap);
    }

    private HttpEntity<byte[]> getListEmptyHttpEntity(String fileName) {
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("filesWrong")
                .filename(fileName)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        return new HttpEntity<>("Hello World".getBytes(), fileMap);
    }


}
