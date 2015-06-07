package me.loki2302;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = App.class)
public class AppTest {
    private static final String INDEX_HTML_CONTAINS_THIS_TEXT = "<!-- THIS IS INDEX.HTML -->";
    private static final String ANGULAR_JS_CONTAINS_THIS_TEXT = "@license AngularJS v1.2.28";

    @Test
    public void indexIsServedAsIndex200() {
        assert200AndIndex(url("/"));
    }

    @Test
    public void page1IsServedAsIndex200() {
        assert200AndIndex(url("/page1"));
    }

    @Test
    public void page2IsServedAsIndex200() {
        assert200AndIndex(url("/page2"));
    }

    @Test
    public void somethingUnderPagesIsServedAsIndex200() {
        assert200AndIndex(url("/pages/omg"));
    }

    @Test
    public void somethingUnderSomethingUnderPagesIsServedAsIndex200() {
        assert404AndIndex(url("/pages/omg/wtf"));
    }

    @Test
    public void somethingUnderRootIsServedAsIndex404() {
        assert404AndIndex(url("/omgwtfbbq"));
    }

    @Test
    public void apiIsServedAsApi200() {
        assert200AndApi(url("/api"));
    }

    @Test
    public void angularIsServedAsAngular200() {
        assert200AndAngular(url("/bower_components/angular/angular.js"));
    }

    private static String url(String path) {
        return String.format("http://localhost:8080" + path);
    }

    private static void assert200AndIndex(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new NullResponseErrorHandler());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains(INDEX_HTML_CONTAINS_THIS_TEXT));
    }

    private static void assert200AndAngular(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new NullResponseErrorHandler());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains(ANGULAR_JS_CONTAINS_THIS_TEXT));
    }

    private static void assert200AndApi(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new NullResponseErrorHandler());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(App.ApiController.API_RESPONDS_WITH_THIS_TEXT, responseEntity.getBody());
    }

    private static void assert404AndIndex(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new NullResponseErrorHandler());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains(INDEX_HTML_CONTAINS_THIS_TEXT));
    }

    public static class NullResponseErrorHandler implements ResponseErrorHandler {
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }

        public void handleError(ClientHttpResponse response) throws IOException {
        }
    }
}
