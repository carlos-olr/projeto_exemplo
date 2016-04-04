package br.com.fatec.projetoweb.web.action;

import com.opensymphony.xwork2.ActionSupport;

import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;

public class TestAction extends ActionSupport {

	private PapelDTO papel;

	/** */
	private static final long serialVersionUID = 1071989853380980252L;

	public String blah() {
		this.papel = new PapelDTO(1l, "papel", "descricao papel",
				new GrupoDTO(1l, "grupo", "desc grupo"));
		return "jsp";
	}

	public PapelDTO getPapel() {
		return this.papel;
	}

	public void setPapel(PapelDTO papel) {
		this.papel = papel;
	}

}
