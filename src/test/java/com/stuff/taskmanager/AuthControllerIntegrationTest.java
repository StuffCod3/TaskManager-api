package com.stuff.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthControllerIntegrationTest {
    private final String baseUrl = "http://localhost:8080/api/v1/auth";
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testRegistration() {
        // Запрос на регистрацию
        String registrationUrl = baseUrl + "/signup";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"email\":\"a@a2.ru\",\"password\":\"1234\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(registrationUrl, requestEntity, String.class);

        // Проверка успешной регистрации
        assertEquals(200, responseEntity.getStatusCodeValue());
        // Дополнительные проверки, если необходимо
    }

    @Test
    public void testAuthentication() {
        // Запрос на аутентификацию
        String authenticationUrl = baseUrl + "/signin";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"email\":\"a@a.ru\",\"password\":\"1234\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(authenticationUrl, requestEntity, String.class);

        // Проверка успешной аутентификации
        assertEquals(200, responseEntity.getStatusCodeValue());
        // Дополнительные проверки, если необходимо
    }
}
