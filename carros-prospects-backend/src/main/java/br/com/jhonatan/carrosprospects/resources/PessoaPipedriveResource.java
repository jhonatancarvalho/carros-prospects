package br.com.jhonatan.carrosprospects.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonatan.carrosprospects.domain.PessoaPipedrive;
import br.com.jhonatan.carrosprospects.services.PessoaPipedriveService;

@RestController
@RequestMapping("/pessoas")
public class PessoaPipedriveResource {

	@Autowired
	private PessoaPipedriveService pessoaPipedriveService;
	
	@GetMapping("{id}")
	public ResponseEntity<PessoaPipedrive> findById(
			@PathVariable Integer id, 
			@RequestParam(value="token", defaultValue="") String token) {
		return ResponseEntity.ok(pessoaPipedriveService.findById(token, id));
	}
	
	@GetMapping
	public ResponseEntity<List<PessoaPipedrive>> findAll(
			@RequestParam(value="token", defaultValue="") String token) {
		return ResponseEntity.ok(pessoaPipedriveService.findAll(token));
	}
	
}
