package br.com.fatec.projetoweb.api.dto;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class UsuarioDTO {

	// atributos
	private Long id;
	private String nome;
	private String senha;
	private List<PapelDTO> papeis = Lists.newArrayList();
	private List<GrupoDTO> grupos = Lists.newArrayList();
	private Set<PapelDTO> papeisUsuario = Sets.newHashSet();
	// flags
	private Boolean isAdmin;

	public UsuarioDTO() {

	}

	public UsuarioDTO(Long id, String nome, String senha) {
		this.id = id;
		this.nome = nome;
		this.senha = senha;
	}

	public boolean possuiPapel(String nomePapel) {
		for (PapelDTO papel : this.getPapeisUsuario()) {
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

	public List<GrupoDTO> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}

	@Override
	public String toString() {
		return "Usuario[" + this.id + " - " + this.nome + "]";
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<PapelDTO> getPapeisUsuario() {
		return papeisUsuario;
	}

	public void setPapeisUsuario(Set<PapelDTO> papeisUsuario) {
		this.papeisUsuario = papeisUsuario;
	}
}
