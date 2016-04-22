package br.com.fatec.projetoweb.web.action;

import java.util.Date;

import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.service.UsuarioService;
import br.com.fatec.projetoweb.web.context.ContextoLogin;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class LoginAction extends ProjetoWebAction {

	/** */
	private static final long serialVersionUID = 7059748509020357437L;
	private static final String DEU_CERTO = "foi";

	private UsuarioService service;
	private ContextoLogin contexto = new ContextoLogin();

	public LoginAction() {
		this.service = ImplFinder.getImpl(UsuarioService.class);
	}

	public String login() {
		UsuarioDTO usuario = this.contexto.getUsuario();
		UsuarioDTO usuarioEncontrado = this.service
				.buscarPorLoginESenha(usuario.getLogin(), usuario.getSenha());

		if (usuarioEncontrado != null) {
			usuarioEncontrado.setStartSession(new Date().getTime());
			this.getSession().put("usuario", usuarioEncontrado);
		}
		this.contexto.setUsuario(usuarioEncontrado);
		return DEU_CERTO;
	}

	public String logout() {
		this.contexto.setUsuario(null);
		this.getSession().remove("usuario");
		return DEU_CERTO;
	}

	public ContextoLogin getContexto() {
		return this.contexto;
	}

	public void setContexto(ContextoLogin contexto) {
		this.contexto = contexto;
	}

}
