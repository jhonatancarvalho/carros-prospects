package br.com.jhonatan.carrosprospects.services;

import java.io.IOException;
import java.util.ArrayList;
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
import br.com.jhonatan.carrosprospects.services.exceptions.IOPipedriveException;

@Service
public class PessoaPipedriveService {

	@Value("${pipedrive.url}")
	private String pipedriveUrl;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public PessoaPipedrive findById(String token, Integer id) {
		final RestTemplate restTemplate = new RestTemplate();	
		final String urlGet = pipedriveUrl + "/persons/" + id + "?api_token=" + token;
		
		final ResponseEntity<String> response = restTemplate.getForEntity(urlGet, String.class);	
		
		JsonNode data;
		PessoaPipedrive pessoaPipedrive;
		try {
			data = objectMapper.readTree(response.getBody()).path("data");
			pessoaPipedrive = objectMapper.treeToValue(data, PessoaPipedrive.class);
		} catch (IOException e) {
			throw new IOPipedriveException("Erro na leitura das requisições no Pipedrive");
		}
	
		return pessoaPipedrive;
	}
	
	public List<PessoaPipedrive> findAll(String token) {
		final RestTemplate restTemplate = new RestTemplate();	
		final String urlGet = pipedriveUrl + "/persons/?api_token=" + token;
		
		final ResponseEntity<String> response = restTemplate.getForEntity(urlGet, String.class);
		
		JsonNode data;
		List<PessoaPipedrive> pessoas = new ArrayList<>();
		try {
			data = objectMapper.readTree(response.getBody()).path("data");
			pessoas = objectMapper.readValue(data.toString(), new TypeReference<List<PessoaPipedrive>>(){});
		} catch (IOException e) {
			throw new IOPipedriveException("Erro na leitura das requisições no Pipedrive");
		}
		
		return pessoas;
	}
	
}
