package de.zuellich.meal_planner.controller;

import de.zuellich.meal_planner.MealPlanner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MealPlanner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParseTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void acceptsAURLParameter() {
        String url = getURL("/parse");
        ResponseEntity<Map> entity = testRestTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        url = getURL("/parse?url=http%3A%2F%2Fexample.com");
        entity = testRestTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void respondsWithErrorWhenNotAValidURL() {
        String url = getURL("/parse?url=test");
        ResponseEntity<Map> entity = testRestTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Create a URL by appending the given parameter to the base URL.
     *
     * @param append The part to append. Note that the base URL does not contain a trailing slash.
     * @return The constructed URL.
     */
    private String getURL(String append) {
        return "http://localhost:" + port + append;
    }
}