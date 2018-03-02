package br.com.jhonatan.carrosprospects.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonatan.carrosprospects.domain.Carro;
import br.com.jhonatan.carrosprospects.repositories.CarroRepository;
import br.com.jhonatan.carrosprospects.services.exceptions.ObjectNotFoundException;

@Service
public class CarroService {

	@Autowired
	private CarroRepository carroRepository;
	
	public Carro findById(Integer id) {
		final Carro carro = carroRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Carro não foi encontrado."));
		
		return carro;
	}
	
	public Carro save(Carro carro) {
		return carroRepository.save(carro);
	}
	
	public Carro update(Carro carro, Integer id) {
		carro.setId(id);
		findById(id);
		return carroRepository.save(carro);
	}
	
	public void delete(Integer id) {
		final Carro carro = findById(id);	
		carroRepository.delete(carro);
	}
	
	public List<Carro> findAll() {
		return carroRepository.findAll();
	}
	
}
