package br.com.jhonatan.carrosprospects.services.validation;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jhonatan.carrosprospects.domain.Carro;
import br.com.jhonatan.carrosprospects.services.validation.error.FieldMessage;

public class CarroValidator implements ConstraintValidator<CarroValidatorAnnotation, Carro> {

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(CarroValidatorAnnotation ann) {}

	@Override
	public boolean isValid(Carro carro, ConstraintValidatorContext context) {
		final List<FieldMessage> listaMensagens = new ArrayList<>();
		
		if (Objects.nonNull(request) && "POST".equals(request.getMethod()) && Objects.nonNull(carro.getId())) {
			listaMensagens.add(new FieldMessage("id", "Não é possível salvar um novo carro com id preenchido"));
		}

		if (carro.getAno() < Year.now().getValue() - 30) {
			listaMensagens.add(new FieldMessage("ano", "Carros com mais de 30 anos não são aceitos"));
		}

		for (FieldMessage fieldMessage : listaMensagens) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
			.addPropertyNode(fieldMessage.getFieldName())
			.addConstraintViolation();
		}

		return listaMensagens.isEmpty();
	}

}