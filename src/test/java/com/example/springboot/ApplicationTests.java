package com.example.springboot;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

class Size {
    private int size;

    public int getSize() {
        return size;
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	private final String ROOT_URL = "http://localhost:";

	@Autowired
	private TestRestTemplate rest;

	@LocalServerPort
	private int port;

	public ResponseEntity<String> callPutEndpoint(String s) {
        String putUrl = ROOT_URL + port + "api/v1/stack/{item}";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        Map<String, String> p = new HashMap<String, String>();
        p.put("item", s);

        ResponseEntity<String> response = rest.exchange(putUrl, HttpMethod.PUT, entity, String.class, p);
        return response;
    }

    public ResponseEntity<String> callDeleteEndpoint() {
        String deleteUrl = ROOT_URL + port + "api/v1/stack";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = rest.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);
        return response;
    }

    @BeforeEach
    public void clearStack() {
        String deleteUrl = ROOT_URL + port + "api/v1/stack/clear";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        rest.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);
    }

    @Test
    public void testGetSize() {
        String getUrl = ROOT_URL + port + "api/v1/stack/size";
        Size s = rest.getForObject(getUrl, Size.class);
        Assert.assertEquals(s.getSize(), 0);
    }

	@Test
	public void testPut() {
        ResponseEntity<String> response = callPutEndpoint("example");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Size s = rest.getForObject(ROOT_URL + port + "api/v1/stack/size", Size.class);
        Assert.assertEquals(s.getSize(), 1);
	}

	@Test
	public void testDelete() {
        ResponseEntity<String> response = callDeleteEndpoint();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        callPutEndpoint("example");
        ResponseEntity<String> newResponse = callDeleteEndpoint();
        Assert.assertEquals(newResponse.getStatusCode(), HttpStatus.OK);
        String s = newResponse.getBody();
        Assert.assertEquals(s, "example");
	}

    @Test
    public void testStackSuccessiveRequests() {
        callPutEndpoint("1");
        callPutEndpoint("2");
        callPutEndpoint("3");
        String getUrl = ROOT_URL + port + "api/v1/stack/size";
        Size size = rest.getForObject(getUrl, Size.class);
        Assert.assertEquals(size.getSize(), 3);
        ResponseEntity<String> r1 = callDeleteEndpoint();
        Assert.assertEquals(r1.getBody(), "3");
        ResponseEntity<String> r2 = callDeleteEndpoint();
        Assert.assertEquals(r2.getBody(), "2");
        ResponseEntity<String> r3 = callDeleteEndpoint();
        Assert.assertEquals(r3.getBody(), "1");
    }
}
