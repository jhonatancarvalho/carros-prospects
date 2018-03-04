package br.com.jhonatan.carrosprospects.enums;

import java.util.Objects;

public enum CorCarro {
	
	BRANCO("Branco"),
	PRETO("Preto"),
	VERDE("Verde");
	
	private String descricao;
	
	private CorCarro(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static CorCarro toEnum(String descricao) {
		if (Objects.isNull(descricao) || descricao.isEmpty()) {
			return null;
		}
		
		for (CorCarro corCarro : CorCarro.values()) {
			if (descricao.equals(corCarro.getDescricao())) {
				return corCarro;
			}
		}
		
		throw new IllegalArgumentException("Cor inválida: " + descricao);
	}
}
