package br.com.fatec.projetoweb.web.action;

import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.service.GrupoService;
import br.com.fatec.projetoweb.api.service.PapelService;
import br.com.fatec.projetoweb.api.service.UsuarioService;
import br.com.fatec.projetoweb.web.context.ContextoUsuario;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class UsuarioAction extends ProjetoWebAction {

	/** */
	private static final long serialVersionUID = 1071989853380980252L;
	private static final String DEU_CERTO = "foi";

	private ContextoUsuario contexto = new ContextoUsuario();
	private UsuarioService service;
	private GrupoService grupoService;
	private PapelService papelService;

	public UsuarioAction() {
		this.service = ImplFinder.getImpl(UsuarioService.class);
		this.grupoService = ImplFinder.getImpl(GrupoService.class);
		this.papelService = ImplFinder.getImpl(PapelService.class);
	}

	public String listar() {
		this.contexto.setUsuarios(this.service.listar());
		return DEU_CERTO;
	}

	public String salvar() {
		if (this.contexto.getUsuario().getId() != null) {
			this.service.atualizar(this.contexto.getUsuario());
		} else {
			this.service.salvar(this.contexto.getUsuario());
		}
		return this.listar();
	}

	public String editar() {
		UsuarioDTO papel = this.service.buscarPorId(this.contexto.getUsuario().getId());
		this.contexto.setUsuario(papel);
		return DEU_CERTO;
	}

	public String deletar() {
		this.service.deletar(this.contexto.getUsuario().getId());
		return this.listar();
	}

	public String listarGruposEPapeis() {
		this.contexto.setGrupos(this.grupoService.listar());
		this.contexto.setPapeis(this.papelService.listar());
		return DEU_CERTO;
	}

	public ContextoUsuario getContexto() {
		return this.contexto;
	}

	public void setContexto(ContextoUsuario contexto) {
		this.contexto = contexto;
	}

}
