package br.com.fatec.projetoweb.web.action;

import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.api.service.GrupoService;
import br.com.fatec.projetoweb.web.context.ContextoGrupo;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class GrupoAction extends ProjetoWebAction {

	/** */
	private static final long serialVersionUID = 1071989853380980252L;
	private static final String DEU_CERTO = "foi";

	private ContextoGrupo contexto = new ContextoGrupo();
	private GrupoService service;

	public GrupoAction() {
		this.service = ImplFinder.getImpl(GrupoService.class);
	}

	public String listar() {
		this.contexto.setGrupos(this.service.listar());
		return DEU_CERTO;
	}

	public String salvar() {
		if (this.contexto.getGrupo().getId() != null) {
			this.service.atualizar(this.contexto.getGrupo());
		} else {
			this.service.salvar(this.contexto.getGrupo());
		}
		return this.listar();
	}

	public String editar() {
		GrupoDTO grupo = this.service.buscarPorId(this.contexto.getGrupo().getId());
		this.contexto.setGrupo(grupo);
		return this.listar();
	}

	public String deletar() {
		this.service.deletar(this.contexto.getGrupo().getId());
		return this.listar();
	}

	public ContextoGrupo getContexto() {
		return this.contexto;
	}

	public void setContexto(ContextoGrupo contexto) {
		this.contexto = contexto;
	}

}
