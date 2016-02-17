
package br.com.fatec.projetoweb.test.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import org.junit.Assert;

import br.com.fatec.projetoweb.api.dao.UsuarioDAO;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.fatec.projetoweb.test.comum.TestBase;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class UsuarioDAOTest extends TestBase {

	private UsuarioDAO dao;

	@Before
	public void config() {
		this.dao = ImplFinder.getImpl(UsuarioDAO.class);
	}

	@Test
	public void testSave1() {

	}

	@Test
	public void testSave() {
		Usuario usu_salvar = new Usuario();
		usu_salvar.setNome("carlos");
		usu_salvar.setSenha("senha_carlos");

		Long id = this.dao.save(usu_salvar);

		Usuario usu_salvo = this.dao.findById(id);

		Assert.assertNotNull(usu_salvo);
		Assert.assertEquals("carlos", usu_salvo.getNome());
		Assert.assertEquals("senha_carlos", usu_salvo.getSenha());
	}

	@Test
	public void testUpdate() {
		Usuario usu_salvar = new Usuario();
		usu_salvar.setNome("carlos");
		usu_salvar.setSenha("senha_carlos");

		Long id = this.dao.save(usu_salvar);
		Usuario usu_atualizar = this.dao.findById(id);

		usu_atualizar.setNome("carlos oliveira");
		usu_atualizar.setSenha("nova_senha");

		this.dao.update(usu_atualizar);
		Usuario usu_atualizado = this.dao.findById(id);

		Assert.assertNotNull(usu_atualizado);
		Assert.assertEquals("carlos oliveira", usu_atualizado.getNome());
		Assert.assertEquals("nova_senha", usu_atualizado.getSenha());
	}

	@Test
	public void testDelete() {
		Usuario usu_salvar = new Usuario();
		usu_salvar.setNome("carlos");
		usu_salvar.setSenha("senha_carlos");

		Long id = this.dao.save(usu_salvar);
		this.dao.delete(id);

		Usuario usu_deletado = this.dao.findById(id);

		Assert.assertNull(usu_deletado);
	}

	@Test
	public void testFindAll() {
		Usuario usu1 = new Usuario();
		usu1.setNome("usuario 1");
		usu1.setSenha("senha_1");
		Usuario usu2 = new Usuario();
		usu2.setNome("usuario 2");
		usu2.setSenha("senha_2");

		this.dao.save(usu1);
		this.dao.save(usu2);

		List<Usuario> encontrados = this.dao.findAll();

		Assert.assertEquals(2, encontrados.size());
		Assert.assertEquals("usuario 1", encontrados.get(0).getNome());
		Assert.assertEquals("senha_1", encontrados.get(0).getSenha());
		Assert.assertEquals("usuario 2", encontrados.get(1).getNome());
		Assert.assertEquals("senha_2", encontrados.get(1).getSenha());
	}

}
