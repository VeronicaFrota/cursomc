package com.veronicafrota.cursomc.domain.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	// Constructor with data.
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	// Receives the code and returns the client-type object.
	public static Perfil toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		// Scrolls through all possible client-type values
		for(Perfil x : Perfil.values()) {
			
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
