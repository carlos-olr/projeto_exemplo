package br.com.fatec.projetoweb.api.dto;

public class PapelDTO {

	private Long id;
	private String nome;
	private String descricao;
	private GrupoPapelDTO grupo;

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

	public GrupoPapelDTO getGrupo() {
		return this.grupo;
	}

	public void setGrupo(GrupoPapelDTO grupo) {
		this.grupo = grupo;
	}

}
