package br.com.alura.adopet.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AbrigoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Deveria devolver código 400 para cadastro com erros")
    void cadastrarCenario01() {
        String json = "{}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Void> response = restTemplate.exchange(
          "/abrigos",
                HttpMethod.POST,
                new HttpEntity<>(json, headers),
                Void.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Deveria devolver código 200 para cadastro sem erros")
    void cadastrarCenario02() {
        String json = """
                {
                    "nome": "Abrigo xpto",
                    "telefone": "61977777777",
                    "email": "abrigoxpto@email.com.br"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/abrigos",
                HttpMethod.POST,
                new HttpEntity<>(json, headers),
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}