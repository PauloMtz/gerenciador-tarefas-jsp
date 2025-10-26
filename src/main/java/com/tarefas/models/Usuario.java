package com.tarefas.models;

import java.time.LocalDate;
import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

import com.tarefas.models.enums.Perfil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50)
	private String nome;
	
	@Column(length = 100, nullable = false, unique = true)
    private String email;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Perfil perfil = Perfil.USER;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;

	public Usuario() {}

	public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        setSenha(senha);
        this.dataCadastro = LocalDate.now();
        this.ativo = true;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (senha != null && !senha.startsWith("$2a$")) {
			this.senha = BCrypt.hashpw(senha, BCrypt.gensalt());
		} else {
			this.senha = senha;
		}
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDate getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDate dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public boolean verificarSenha(String senha) {
	    return senha != null && this.senha != null && BCrypt.checkpw(senha, this.senha);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario: [\nid = " + id + ", nome = " + nome + "]\n";
	}
}
