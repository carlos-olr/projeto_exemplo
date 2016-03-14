package br.com.fatec.projetoweb.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.test.comum.TestCenario;

public class PapelServiceTest extends TestCenario {

	@Test
	public void salvar() {
		PapelDTO dto = new PapelDTO(null, "Papel_1", "papel teste",
				this.grupoDtos.get(1l));

		PapelDTO salvo = this.papelService.salvar(dto);
		salvo = this.papelService.buscarPorId(salvo.getId());
		Assert.assertEquals(new Long(9), salvo.getId());
		Assert.assertEquals("Papel_1", salvo.getNome());
		Assert.assertEquals("papel teste", salvo.getDescricao());
		Assert.assertEquals("Contabilidade", salvo.getGrupo().getNome());
	}

	@Test
	public void atualizar() {
		PapelDTO dto = this.papelDtos.get(1L);

		dto.setNome("outro papel");
		dto.setDescricao("nova descrição");
		dto.setGrupo(this.grupoDtos.get(2l));

		this.papelService.atualizar(dto);
		dto = this.papelService.buscarPorId(dto.getId());

		Assert.assertEquals(new Long(1), dto.getId());
		Assert.assertEquals("outro papel", dto.getNome());
		Assert.assertEquals("nova descrição", dto.getDescricao());
		Assert.assertEquals("Recursos Humanos", dto.getGrupo().getNome());
	}

	@Test
	public void deletar() {
		PapelDTO dto = this.papelDtos.get(1L);

		this.papelService.deletar(dto.getId());
	}

	@Test
	public void listar() {
		List<PapelDTO> papels = this.papelService.listar();

		Assert.assertEquals(8, papels.size());
		Assert.assertEquals("ADMIN", papels.get(0).getNome());
		Assert.assertEquals("CRUD_CONTAB", papels.get(1).getNome());
		Assert.assertEquals("CRUD_RH", papels.get(2).getNome());
	}

}