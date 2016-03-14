package br.com.fatec.projetoweb.api.dto;

public class PapelDTO {

	private Long id;
	private String nome;
	private String descricao;
	private GrupoDTO grupo;

	public PapelDTO() {

	}

	public PapelDTO(Long id, String nome, String descricao, GrupoDTO grupo) {
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

	public GrupoDTO getGrupo() {
		return this.grupo;
	}

	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		return "Papel[" + this.id + " - " + this.nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.descricao == null) ? 0 : this.descricao.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.nome == null) ? 0 : this.nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		PapelDTO other = (PapelDTO) obj;
		if (this.descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!this.descricao.equals(other.descricao))
			return false;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.nome == null) {
			if (other.nome != null)
				return false;
		} else if (!this.nome.equals(other.nome))
			return false;
		return true;
	}

}
