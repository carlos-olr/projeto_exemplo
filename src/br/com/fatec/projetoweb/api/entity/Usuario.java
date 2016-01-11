package br.com.fatec.projetoweb.api.entity;

public class Usuario {

	public static final String TABLE = "PROJETO_TESTE_USUARIO";
	public static final String COL_ID = "ID";
	public static final String COL_NOME = "NOME";
	public static final String COL_SENHA = "SENHA";

	private Long id;
	private String nome;
	private String senha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		this.senha = senha;
	}

}
