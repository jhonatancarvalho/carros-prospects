package br.com.jhonatan.carrosprospects.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = { "classpath:application.properties" })
public class PipedriveTest {

	@Value("${pipedrive.url}")
	private String pipedriveUrl;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void deveBuscarPessoasPipedrive() throws IOException {
		final String token = "857bba6c326cb2246f922baa49db30a634883711";
		final RestTemplate restTemplate = new RestTemplate();
		final String urlGet = pipedriveUrl + "/persons/?api_token=" + token;

		final ResponseEntity<String> response = restTemplate.getForEntity(urlGet, String.class);
		final JsonNode data = objectMapper.readTree(response.getBody()).path("data");
		
		assertThat(data.asText(), notNullValue());
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
	
	@Test
	public void naoDeveBuscarPessoasPipedriveComTokenInvalido() throws IOException {
		final String token = "tokenInvalido";
		final RestTemplate restTemplate = new RestTemplate();
		final String urlGet = pipedriveUrl + "/persons/?api_token=" + token;
		
		exception.expect(HttpClientErrorException.class);
		exception.expectMessage("401 Unauthorized");

		restTemplate.getForEntity(urlGet, String.class);
	}
	
	@Test
	public void naoDeveBuscarPessoasPipedriveComIdInvalido() throws IOException {
		final String token = "857bba6c326cb2246f922baa49db30a634883711";
		final Integer id = -1;
		final RestTemplate restTemplate = new RestTemplate();
		final String urlGet = pipedriveUrl + "/persons/" + id + "?api_token=" + token;
		
		exception.expect(HttpClientErrorException.class);
		exception.expectMessage("404 Not Found");

		restTemplate.getForEntity(urlGet, String.class);
	}
}
