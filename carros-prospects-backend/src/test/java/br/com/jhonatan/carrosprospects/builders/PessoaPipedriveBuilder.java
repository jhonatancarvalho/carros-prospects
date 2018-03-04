package br.com.jhonatan.carrosprospects.builders;

import br.com.jhonatan.carrosprospects.domain.PessoaPipedrive;

public class PessoaPipedriveBuilder {

	private PessoaPipedrive pessoaPipedrive;
	
	private PessoaPipedriveBuilder() {}
	
	public static PessoaPipedriveBuilder builder() {
		final PessoaPipedriveBuilder pessoaPipedriveBuilder = new PessoaPipedriveBuilder();
		pessoaPipedriveBuilder.pessoaPipedrive = new PessoaPipedrive();
		pessoaPipedriveBuilder.pessoaPipedrive.setId(1);
		pessoaPipedriveBuilder.pessoaPipedrive.setName("Jhonatan");
		return pessoaPipedriveBuilder;
	}

	public PessoaPipedrive build() {
		return pessoaPipedrive;
	}
}
