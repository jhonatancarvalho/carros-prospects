package br.com.jhonatan.carrosprospects.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import br.com.jhonatan.carrosprospects.enums.CorCarro;
import br.com.jhonatan.carrosprospects.services.validation.CarroValidatorAnnotation;

@Entity
@CarroValidatorAnnotation
public class Carro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="Preenchimento obrigatório")
	private Integer pessoaId;
	
	@NotNull(message="Preenchimento obrigatório")
	private String modelo;
	
	@NotNull(message="Preenchimento obrigatório")
	private Integer ano;
	
	@NotNull(message="Preenchimento obrigatório")
	private String cor;
	
	public Carro() {
		super();
	}
	
	public Carro(Integer id, Integer pessoaId, String modelo, Integer ano, CorCarro cor) {
		this.id = id;
		this.pessoaId = pessoaId;
		this.modelo = modelo;
		this.ano = ano;
		this.cor = Objects.isNull(cor) ? null : cor.getDescricao();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPessoaId() {
		return pessoaId;
	}
	
	public void setPessoaId(Integer pessoaId) {
		this.pessoaId = pessoaId;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public Integer getAno() {
		return ano;
	}
	
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public CorCarro getCor() {
		return CorCarro.toEnum(cor);
	}
	
	public void setCor(CorCarro cor) {
		this.cor = cor.getDescricao();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carro other = (Carro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
