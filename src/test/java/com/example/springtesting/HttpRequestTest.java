package com.example.springtesting;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void canAdd() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=2", String.class))
                .isEqualTo("3.0");
    }

    @Test
    public void catAddWithMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void catAddWithEmptyValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void catAddWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1.5&b=2", String.class))
                .isEqualTo("3.5");
    }

    @Test
    public void catAddWithInvalidNumber() throws JSONException {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=X", String.class))
                .contains(":400");
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/add?a=1&b=X", String.class)
                .getStatusCode()
                .value()
        ).isEqualTo(400);
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/add?a=1&b=X", String.class)
                .getStatusCode()
                .is4xxClientError()
        ).isTrue();
        JSONAssert.assertEquals(
                "{status:400}",
                this.restTemplate.getForEntity("http://localhost:" + port + "/add?a=1&b=X", String.class).getBody(),
                false
        );
    }

    @Test
    public void catAddNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=-2", String.class))
                .isEqualTo("-1.0");
    }

    @DisplayName("multiple additions")
    @ParameterizedTest(name="{displayName} [{index}] {0} + {1} = {2}")
    @CsvSource({
            "1,     2,  3.0",
            "1,     1,  2.0",
            "1.0, 1.0,  2.0",
            "1,    -2, -1.0",
            "1,  -1.0,  0.0",
            "1,    '',  1.0"
    })
    void canAddParameterized(String a, String b, String expected) {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=" + a + "&b=" + b, String.class))
                .isEqualTo(expected);
    }

    @Test
    public void canSubtract() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=1&b=2", String.class))
                .isEqualTo("-1.0");
    }

    @Test
    public void canMultiply() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=1&b=2", String.class))
                .isEqualTo("2.0");
    }

    @Test
    public void canDivide() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=1&b=2", String.class))
                .isEqualTo("0.5");
    }
    @Test
    public void ResponseGreting() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/greeting", String.class))
                .isEqualTo("Hello, Hello");

    }

}