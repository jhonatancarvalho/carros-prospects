package br.com.jhonatan.carrosprospects.builders;

import br.com.jhonatan.carrosprospects.domain.Carro;
import br.com.jhonatan.carrosprospects.enums.CorCarro;

public class CarroBuilder {

	private Carro carro;
	
	private CarroBuilder() {}
	
	public static CarroBuilder builder() {
		final CarroBuilder carroBuilder = new CarroBuilder();
		carroBuilder.carro = new Carro(null, 2, "ONIX", 2013, CorCarro.BRANCO);
		return carroBuilder;
	}
	
	public CarroBuilder comId(Integer id) {
		carro.setId(id);
		return this;
	}
	
	public Carro build() {
		return carro;
	}
}
