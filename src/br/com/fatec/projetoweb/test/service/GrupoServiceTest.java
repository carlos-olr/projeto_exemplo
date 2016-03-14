package br.com.fatec.projetoweb.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.test.comum.TestCenario;

public class GrupoServiceTest extends TestCenario {

	@Test
	public void salvar() {
		GrupoDTO dto = new GrupoDTO(null, "Grupo 1", "Não sei o que por");

		GrupoDTO salvo = this.grupoService.salvar(dto);
		salvo = this.grupoService.buscarPorId(salvo.getId());
		Assert.assertEquals(new Long(4), salvo.getId());
		Assert.assertEquals("Grupo 1", salvo.getNome());
		Assert.assertEquals("Não sei o que por", salvo.getDescricao());
	}

	@Test
	public void atualizar() {
		GrupoDTO dto = this.grupoDtos.get(1L);

		dto.setNome("outro grupo");
		dto.setDescricao("nova descrição");

		this.grupoService.atualizar(dto);
		dto = this.grupoService.buscarPorId(dto.getId());

		Assert.assertEquals(new Long(1), dto.getId());
		Assert.assertEquals("outro grupo", dto.getNome());
		Assert.assertEquals("nova descrição", dto.getDescricao());
	}

	@Test(expected = RuntimeException.class)
	public void deletar() {
		GrupoDTO dto = this.grupoDtos.get(1L);

		this.grupoService.deletar(dto.getId());
	}

	public void deletar$OK() {
		GrupoDTO dto = new GrupoDTO(null, "Grupo 1", "Não sei o que por");

		GrupoDTO salvo = this.grupoService.salvar(dto);
		this.grupoService.deletar(salvo.getId());
		dto = this.grupoService.buscarPorId(salvo.getId());

		Assert.assertNull(dto);
	}

	@Test
	public void listar() {
		List<GrupoDTO> grupos = this.grupoService.listar();

		Assert.assertEquals(3, grupos.size());
		Assert.assertEquals("Contabilidade", grupos.get(0).getNome());
		Assert.assertEquals("Recursos Humanos", grupos.get(1).getNome());
		Assert.assertEquals("Informática", grupos.get(2).getNome());
	}

}