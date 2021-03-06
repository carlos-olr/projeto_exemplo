package br.com.fatec.projetoweb.api.dto;

public class GrupoDTO {

	private Long id;
	private String nome;
	private String descricao;

	public GrupoDTO() {

	}

	public GrupoDTO(Long id, String nome, String descricao) {
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

	@Override
	public String toString() {
		return "Grupo[" + this.id + " - " + this.nome + "]";
	}

}
