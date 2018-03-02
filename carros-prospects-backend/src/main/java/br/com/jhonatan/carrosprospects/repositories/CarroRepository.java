package br.com.jhonatan.carrosprospects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonatan.carrosprospects.domain.Carro;

public interface CarroRepository extends JpaRepository<Carro, Integer> {

}
