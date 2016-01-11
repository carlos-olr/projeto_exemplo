package br.com.fatec.projetoweb.api.entity;

public class Papel {

	public static final String TABLE = "PROJETO_TESTE_PAPEL";
	public static final String COL_ID = "ID";
	public static final String COL_NOME = "NOME";
	public static final String COL_DESCRICAO = "DESCRICAO";
	public static final String COL_GRUPO_ID = "GRUPO_ID";

	private Long id;
	private String nome;
	private String descricao;
	private GrupoPapel grupo;

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

	public GrupoPapel getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoPapel grupo) {
		this.grupo = grupo;
	}

}
