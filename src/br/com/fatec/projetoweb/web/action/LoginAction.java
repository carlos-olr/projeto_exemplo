package br.com.fatec.projetoweb.web.action;

import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.service.UsuarioService;
import br.com.fatec.projetoweb.web.context.ContextoLogin;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class LoginAction extends ProjetoWebAction {

	/** */
	private static final long serialVersionUID = 7059748509020357437L;

	private UsuarioService service;
	private ContextoLogin contexto = new ContextoLogin();

	public LoginAction() {
		this.service = ImplFinder.getImpl(UsuarioService.class);
	}

	public String doLogin() {
		UsuarioDTO usuario = this.contexto.getUsuario();
		UsuarioDTO usuarioEncontrado = this.service
				.buscarPorLoginESenha(usuario.getLogin(), usuario.getSenha());
		this.contexto.setUsuario(usuarioEncontrado);
		this.getSession().put("usuario", usuarioEncontrado);
		return SUCCESS;
	}

	public ContextoLogin getContexto() {
		return this.contexto;
	}

	public void setContexto(ContextoLogin contexto) {
		this.contexto = contexto;
	}

}
