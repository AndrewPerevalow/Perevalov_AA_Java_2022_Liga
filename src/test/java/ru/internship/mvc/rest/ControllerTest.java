package ru.internship.mvc.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test invalid input command")
        void getCommand_InvalidInput_Incorrect() throws Exception {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    new URL("http://localhost:" + port + "/cli?command=incorrectcommand").toString(), String.class);
            assertEquals("Incorrect input values", response.getBody());
        }
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test valid input command")
        void getCommand_ValidInput_Success() throws Exception {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    new URL("http://localhost:" + port + "/cli?command=printall_withoutfilter 4").toString(), String.class);
            assertEquals("Задание: id = 5; Заголовок = 'Выполнить ДЗ'; Описание = 'Придумать и написать игру'; Срок выполения = 2022-08-27 00:00:00.0; Статус = 'Новое'.\n" +
                         "Задание: id = 6; Заголовок = 'Выполнить ДЗ'; Описание = 'Придумать и написать другую игру'; Срок выполения = 2022-08-27 00:00:00.0; Статус = 'Новое'.\n" +
                         "Задание: id = 7; Заголовок = 'Выполнить ДЗ'; Описание = 'Придумать и написать еще другую игру'; Срок выполения = 2022-08-27 00:00:00.0; Статус = 'Новое'.\n",
                    response.getBody()
            );
        }
    }
}