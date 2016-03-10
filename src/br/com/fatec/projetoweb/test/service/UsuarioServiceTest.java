package br.com.fatec.projetoweb.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.test.comum.TestCenario;

public class UsuarioServiceTest extends TestCenario {

	@Test
	public void salvar() {
		UsuarioDTO dto = new UsuarioDTO(null, "John", "Cena_WWE");
		dto.setPapeis(this.getPapeis(1l));
		dto.setGrupos(this.getGrupos(2l, 3l));

		UsuarioDTO salvo = this.usuarioService.salvar(dto);

		Assert.assertEquals(new Long(4), salvo.getId());
		Assert.assertEquals("John", salvo.getNome());
		Assert.assertEquals("Cena_WWE", salvo.getSenha());
		Assert.assertTrue(salvo.getIsAdmin());

		Assert.assertEquals(1, salvo.getPapeis().size());
		Assert.assertEquals(new Long(1L), salvo.getPapeis().get(0).getId());

		Assert.assertEquals(2, salvo.getGrupos().size());
		Assert.assertEquals(new Long(2), salvo.getGrupos().get(0).getId());
		Assert.assertEquals(new Long(3), salvo.getGrupos().get(1).getId());

		Assert.assertEquals(6, salvo.getPapeisCompilados().size());
		ArrayList<PapelDTO> compilados = Lists.newArrayList(salvo.getPapeisCompilados());
		Assert.assertEquals(new Long(1), compilados.get(0).getId());
		// papeis grupos 2
		Assert.assertEquals(new Long(3), compilados.get(1).getId());
		Assert.assertEquals(new Long(6), compilados.get(2).getId());
		// papeis grupos 3
		Assert.assertEquals(new Long(4), compilados.get(3).getId());
		Assert.assertEquals(new Long(7), compilados.get(4).getId());
		Assert.assertEquals(new Long(8), compilados.get(5).getId());
	}

	@Test
	public void atualizar() {
		UsuarioDTO dto = this.usuarioDtos.get(1L);

		dto.setNome("Carlos Augusto");
		dto.setSenha("blahblah");
		dto.setPapeis(this.getPapeis(3l));
		dto.setGrupos(this.getGrupos(1l));

		UsuarioDTO atualizado = this.usuarioService.atualizar(dto);

		Assert.assertEquals(new Long(1), atualizado.getId());
		Assert.assertEquals("Carlos Augusto", atualizado.getNome());
		Assert.assertEquals("blahblah", atualizado.getSenha());
		Assert.assertFalse(atualizado.getIsAdmin());

	}

	@Test
	public void deletar() {
		UsuarioDTO dto = this.usuarioDtos.get(1L);
		dto.setPapeis(this.getPapeis(1l));
		dto.setGrupos(this.getGrupos(2l, 3l));

		UsuarioDTO salvo = this.usuarioService.salvar(dto);

		this.usuarioService.deletar(salvo.getId());
	}

	@Test
	public void listar() {
		List<UsuarioDTO> usuarios = this.usuarioService.listar();

		Assert.assertEquals(3, usuarios.size());
		Assert.assertEquals("Carlos", usuarios.get(0).getNome());
		Assert.assertEquals("Augusto", usuarios.get(1).getNome());
		Assert.assertEquals("Argemiro", usuarios.get(2).getNome());
	}

}