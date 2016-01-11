package br.com.fatec.projetoweb.api.entity;

public class GrupoPapel {

	public static final String TABLE = "PROJETO_TESTE_GRUPO_PAPEL";
	public static final String COL_ID = "ID";
	public static final String COL_NOME = "NOME";
	public static final String COL_DESCRICAO = "DESCRICAO";

	private Long id;
	private String nome;
	private String descricao;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
