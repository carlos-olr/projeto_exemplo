package br.com.fatec.projetoweb.web.action;

import com.opensymphony.xwork2.ActionSupport;

import br.com.fatec.projetoweb.api.service.GrupoService;
import br.com.fatec.projetoweb.web.context.ContextoGrupo;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class GrupoAction extends ActionSupport {

	/** */
	private static final long serialVersionUID = 1071989853380980252L;

	private ContextoGrupo contexto = new ContextoGrupo();
	private GrupoService service;

	public GrupoAction() {
		this.service = ImplFinder.getImpl(GrupoService.class);
	}

	public String listar() {
		this.contexto.setGrupos(this.service.listar());
		return SUCCESS;
	}

	public ContextoGrupo getContexto() {
		return this.contexto;
	}

	public void setContexto(ContextoGrupo contexto) {
		this.contexto = contexto;
	}

}
