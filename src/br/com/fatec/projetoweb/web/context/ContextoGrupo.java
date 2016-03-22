package br.com.fatec.projetoweb.web.context;

import java.io.Serializable;
import java.util.List;

import br.com.fatec.projetoweb.api.dto.GrupoDTO;

public class ContextoGrupo implements Serializable {

	/** */
	private static final long serialVersionUID = -4946584307036887149L;

	private GrupoDTO grupo;
	private List<GrupoDTO> grupos;

	public GrupoDTO getGrupo() {
		return this.grupo;
	}

	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}

	public List<GrupoDTO> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}

}
