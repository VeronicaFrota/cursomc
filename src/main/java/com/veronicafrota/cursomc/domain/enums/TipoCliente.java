package com.veronicafrota.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Juridica");
	
	private int cod;
	private String descricao;
	
	// Constructor with data.
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	// Receives the code and returns the client-type object.
	public static TipoCliente toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		// Scrolls through all possible client-type values
		for(TipoCliente x : TipoCliente.values()) {
			
			// See all possibilities of TipoCliente, if the cod is equal to the enum cod
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido" + cod);
	}

	// Getters
	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}	
	
}
