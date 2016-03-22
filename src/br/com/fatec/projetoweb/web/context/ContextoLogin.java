package br.com.fatec.projetoweb.web.context;

import java.io.Serializable;

import br.com.fatec.projetoweb.api.dto.UsuarioDTO;

public class ContextoLogin implements Serializable {

	/** */
	private static final long serialVersionUID = -8312914584176535160L;

	private UsuarioDTO usuario;

	public UsuarioDTO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

}
