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
		salvo = this.usuarioService.buscarPorId(salvo.getId());
		Assert.assertEquals(new Long(4), salvo.getId());
		Assert.assertEquals("John", salvo.getNome());
		Assert.assertEquals("Cena_WWE", salvo.getSenha());
		Assert.assertTrue(salvo.getIsAdmin());

		Assert.assertEquals(1, salvo.getPapeis().size());
		Assert.assertEquals(new Long(1L), salvo.getPapeis().get(0).getId());

		Assert.assertEquals(2, salvo.getGrupos().size());
		Assert.assertEquals(new Long(2), salvo.getGrupos().get(0).getId());
		Assert.assertEquals(new Long(3), salvo.getGrupos().get(1).getId());

		Assert.assertEquals(6, salvo.getPapeisUsuario().size());
		ArrayList<PapelDTO> papeisUsuario = Lists.newArrayList(salvo.getPapeisUsuario());
		Assert.assertEquals(new Long(1), papeisUsuario.get(0).getId());
		// papeis grupos 2
		Assert.assertEquals(new Long(3), papeisUsuario.get(1).getId());
		Assert.assertEquals(new Long(6), papeisUsuario.get(2).getId());
		// papeis grupos 3
		Assert.assertEquals(new Long(4), papeisUsuario.get(3).getId());
		Assert.assertEquals(new Long(7), papeisUsuario.get(4).getId());
		Assert.assertEquals(new Long(8), papeisUsuario.get(5).getId());
	}

	@Test
	public void atualizar() {
		UsuarioDTO dto = this.usuarioDtos.get(1L);

		dto.setNome("Carlos Augusto");
		dto.setSenha("blahblah");
		dto.setPapeis(this.getPapeis(3l));
		dto.setGrupos(this.getGrupos(1l));

		this.usuarioService.atualizar(dto);
		dto = this.usuarioService.buscarPorId(dto.getId());

		Assert.assertEquals(new Long(1), dto.getId());
		Assert.assertEquals("Carlos Augusto", dto.getNome());
		Assert.assertEquals("blahblah", dto.getSenha());
		Assert.assertFalse(dto.getIsAdmin());

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