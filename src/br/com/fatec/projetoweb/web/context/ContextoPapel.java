package br.com.fatec.projetoweb.web.context;

import java.io.Serializable;
import java.util.List;

import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;

public class ContextoPapel implements Serializable {

	/** */
	private static final long serialVersionUID = -4946584307036887149L;

	private PapelDTO papel;
	private List<PapelDTO> papeis;
	private List<GrupoDTO> grupos;

	public PapelDTO getPapel() {
		return this.papel;
	}

	public void setPapel(PapelDTO papel) {
		this.papel = papel;
	}

	public List<PapelDTO> getPapeis() {
		return this.papeis;
	}

	public void setPapeis(List<PapelDTO> papeis) {
		this.papeis = papeis;
	}

	public List<GrupoDTO> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}

}
