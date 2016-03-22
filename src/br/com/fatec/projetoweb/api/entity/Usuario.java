package br.com.fatec.projetoweb.api.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class Usuario {

	public static final String TABLE = "PROJETO_USUARIO";
	public static final String COL_ID = "ID";
	public static final String COL_NOME = "NOME";
	public static final String COL_SENHA = "SENHA";
	public static final String COL_LOGIN = "LOGIN";

	private Long id;
	private String nome;
	private String senha;
	private String login;

	public Usuario() {

	}

	public Usuario(Long id, String nome, String senha, String login) {
		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.login = login;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public static List<String> getColunas() {
		return Lists.newArrayList(COL_ID, COL_NOME, COL_SENHA, COL_LOGIN);
	}

	public static String[] getColunasArray() {
		return new String[] { COL_ID, COL_NOME, COL_SENHA, COL_LOGIN };
	}

}
