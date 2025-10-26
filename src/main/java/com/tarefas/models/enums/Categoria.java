package com.tarefas.models.enums;

public enum Categoria {
	
    MANUT_PREDIAL("Manutenção Predial"),
    LIMPEZA("Limpeza"),
    JARDINAGEM("Jardinagem"),
    RESERVAS("Reservas");

    private String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
