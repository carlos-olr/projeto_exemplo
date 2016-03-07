package br.com.fatec.projetoweb.api.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class Papel {

	public static final String TABLE = "PROJETO_PAPEL";
	public static final String COL_ID = "ID";
	public static final String COL_NOME = "NOME";
	public static final String COL_DESCRICAO = "DESCRICAO";
	public static final String COL_GRUPO_ID = "GRUPO_ID";

	private Long id;
	private String nome;
	private String descricao;
	private GrupoPapel grupo;

	public Papel() {
	}

	public Papel(Long id) {
		this.id = id;
	}

	public Papel(Long id, String nome, String descricao, GrupoPapel grupo) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.grupo = grupo;
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

	public GrupoPapel getGrupo() {
		return this.grupo;
	}

	public void setGrupo(GrupoPapel grupo) {
		this.grupo = grupo;
	}

	public static List<String> getColunas() {
		return Lists.newArrayList(COL_ID, COL_NOME, COL_DESCRICAO, COL_GRUPO_ID);
	}

	public static String[] getColunasArray() {
		return new String[] { COL_ID, COL_NOME, COL_DESCRICAO, COL_GRUPO_ID };
	}

}
