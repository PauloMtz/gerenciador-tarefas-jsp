package com.tarefas.models.enums;

public enum Perfil {
    ADMIN("Administrador"),
    USER("Usuário"),
    TECN("Técnico");
	
	private String descricao;

	Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
