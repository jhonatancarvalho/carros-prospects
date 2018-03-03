package br.com.jhonatan.carrosprospects.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jhonatan.carrosprospects.domain.Carro;
import br.com.jhonatan.carrosprospects.enums.CorCarro;
import br.com.jhonatan.carrosprospects.services.CarroService;

@RestController
@RequestMapping("/carros")
public class CarroResource {

	@Autowired
	private CarroService carroService;
	
	@GetMapping("{id}")
	public ResponseEntity<Carro> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(carroService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody Carro carro) {
		carroService.save(carro);
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(carro.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Carro carro, @PathVariable Integer id) {
		carroService.update(carro, id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		carroService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Carro>> findAll() {
		return ResponseEntity.ok(carroService.findAll());
	}
	
	@GetMapping("/cores")
	public ResponseEntity<CorCarro[]> findCores() {
		return ResponseEntity.ok(CorCarro.values());
	}
	
}
