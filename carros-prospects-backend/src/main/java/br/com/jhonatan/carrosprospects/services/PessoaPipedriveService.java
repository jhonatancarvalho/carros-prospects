package br.com.jhonatan.carrosprospects.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhonatan.carrosprospects.domain.PessoaPipedrive;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class PessoaPipedriveService {

	@Value("${pipedrive.url}")
	private String pipedriveUrl;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public PessoaPipedrive findById(String token, Integer id) throws IOException, ObjectNotFoundException {
		final RestTemplate restTemplate = new RestTemplate();	
		final String urlGet = pipedriveUrl + "/persons/" + id + "?api_token=" + token;
		
		final ResponseEntity<String> response = restTemplate.getForEntity(urlGet, String.class);	
		final JsonNode data = objectMapper.readTree(response.getBody()).path("data");
	
		return objectMapper.treeToValue(data, PessoaPipedrive.class);
	}
	
	public List<PessoaPipedrive> findAll(String token) throws IOException, ObjectNotFoundException {
		final RestTemplate restTemplate = new RestTemplate();	
		final String urlGet = pipedriveUrl + "/persons/?api_token=" + token;
		
		final ResponseEntity<String> response = restTemplate.getForEntity(urlGet, String.class);
		final JsonNode data = objectMapper.readTree(response.getBody()).path("data");
		
		return objectMapper.readValue(data.toString(), new TypeReference<List<PessoaPipedrive>>(){});
	}
	
}
