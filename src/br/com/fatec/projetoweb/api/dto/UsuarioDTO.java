package br.com.fatec.projetoweb.api.dto;

import java.util.List;

public class UsuarioDTO {

	// atributos
	private Long id;
	private String nome;
	private String senha;
	private List<PapelDTO> papeis;

	// flags
	private Boolean isAdmin;

	// getters e setters

	public boolean possuiPapel(String nomePapel) {
		for (PapelDTO papel : this.papeis) {
			if (papel.getNome().equals(nomePapel))
				return true;
		}
		return false;
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

	public List<PapelDTO> getPapeis() {
		return this.papeis;
	}

	public void setPapeis(List<PapelDTO> papeis) {
		this.papeis = papeis;
	}

	public Boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
