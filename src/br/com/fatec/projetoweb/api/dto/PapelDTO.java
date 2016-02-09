package br.com.fatec.projetoweb.api.dto;

import br.com.fatec.projetoweb.api.entity.GrupoPapel;

public class PapelDTO {

	private Long id;
	private String nome;
	private String descricao;
	private GrupoPapel grupo;

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

}
