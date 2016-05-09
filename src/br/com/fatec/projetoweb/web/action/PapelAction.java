package br.com.fatec.projetoweb.web.action;

import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.service.GrupoService;
import br.com.fatec.projetoweb.api.service.PapelService;
import br.com.fatec.projetoweb.web.context.ContextoPapel;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class PapelAction extends ProjetoWebAction {

	/** */
	private static final long serialVersionUID = 1071989853380980252L;
	private static final String DEU_CERTO = "foi";

	private ContextoPapel contexto = new ContextoPapel();
	private PapelService service;
	private GrupoService grupoService;

	public PapelAction() {
		this.service = ImplFinder.getImpl(PapelService.class);
		this.grupoService = ImplFinder.getImpl(GrupoService.class);
	}

	public String listar() {
		this.contexto.setPapeis(this.service.listar());
		return DEU_CERTO;
	}

	public String salvar() {
		if (this.contexto.getPapel().getId() != null) {
			this.service.atualizar(this.contexto.getPapel());
		} else {
			this.service.salvar(this.contexto.getPapel());
		}
		return this.listar();
	}

	public String editar() {
		PapelDTO papel = this.service.buscarPorId(this.contexto.getPapel().getId());
		this.contexto.setPapel(papel);
		return DEU_CERTO;
	}

	public String deletar() {
		this.service.deletar(this.contexto.getPapel().getId());
		return this.listar();
	}

	public String listarGrupos() {
		this.contexto.setGrupos(this.grupoService.listar());
		return DEU_CERTO;
	}

	public ContextoPapel getContexto() {
		return this.contexto;
	}

	public void setContexto(ContextoPapel contexto) {
		this.contexto = contexto;
	}

}
