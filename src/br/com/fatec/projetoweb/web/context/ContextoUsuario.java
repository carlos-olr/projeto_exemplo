package br.com.fatec.projetoweb.web.context;

import java.io.Serializable;
import java.util.List;

import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.dto.UsuarioDTO;

public class ContextoUsuario implements Serializable {

	/** */
	private static final long serialVersionUID = -4946584307036887149L;

	private UsuarioDTO usuario;
	private List<GrupoDTO> grupos;
	private List<UsuarioDTO> usuarios;
	private List<PapelDTO> papeis;

	public UsuarioDTO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public List<GrupoDTO> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}

	public List<UsuarioDTO> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public List<PapelDTO> getPapeis() {
		return this.papeis;
	}

	public void setPapeis(List<PapelDTO> papeis) {
		this.papeis = papeis;
	}

}
