package br.com.fatec.projetoweb.api.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class Grupo {

	public static final String TABLE = "PROJETO_GRUPO_PAPEL";
	public static final String COL_ID = "ID";
	public static final String COL_NOME = "NOME";
	public static final String COL_DESCRICAO = "DESCRICAO";

	private Long id;
	private String nome;
	private String descricao;

	public Grupo() {
	}

	public Grupo(Long id) {
		this.id = id;
	}

	public Grupo(Long id, String nome, String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
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

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static List<String> getColunas() {
		return Lists.newArrayList(COL_ID, COL_NOME, COL_DESCRICAO);
	}

	public static String[] getColunasArray() {
		return new String[] { COL_ID, COL_NOME, COL_DESCRICAO };
	}

}
