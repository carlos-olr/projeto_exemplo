
package br.com.fatec.projetoweb.test.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import org.junit.Assert;

import br.com.fatec.projetoweb.api.dao.GrupoDAO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.fatec.projetoweb.test.comum.TestBase;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class GrupoDAOTest extends TestBase {

	private GrupoDAO dao;

	@Before
	public void config() {
		this.dao = ImplFinder.getImpl(GrupoDAO.class);
	}

	@Test
	public void testSave() {
		Grupo grupo_salvar = new Grupo();
		grupo_salvar.setNome("grupo");
		grupo_salvar.setDescricao("grupo de teste");

		Long id = this.dao.save(grupo_salvar);

		Grupo grupo_salvo = this.dao.findById(id);

		Assert.assertNotNull(grupo_salvo);
		Assert.assertEquals("grupo", grupo_salvo.getNome());
		Assert.assertEquals("grupo de teste", grupo_salvo.getDescricao());
	}

	@Test
	public void testUpdate() {
		Grupo grupo_salvar = new Grupo();
		grupo_salvar.setNome("grupo 1");
		grupo_salvar.setDescricao("grupo de teste");

		Long id = this.dao.save(grupo_salvar);
		Grupo grupo_atualizar = this.dao.findById(id);

		grupo_atualizar.setNome("grupo 1 modificado");
		grupo_atualizar.setDescricao("grupo de teste modificado");

		this.dao.update(grupo_atualizar);
		Grupo grupo_atualizado = this.dao.findById(id);

		Assert.assertNotNull(grupo_atualizado);
		Assert.assertEquals("grupo 1 modificado", grupo_atualizado.getNome());
		Assert.assertEquals("grupo de teste modificado", grupo_atualizado.getDescricao());
	}

	@Test
	public void testDelete() {
		Grupo grupo_salvar = new Grupo();
		grupo_salvar.setNome("grupo");
		grupo_salvar.setDescricao("grupo de teste");

		Long id = this.dao.save(grupo_salvar);
		this.dao.delete(id);

		Grupo grupo_deletado = this.dao.findById(id);

		Assert.assertNull(grupo_deletado);
	}

	@Test
	public void testFindAll() {
		Grupo grupo1 = new Grupo();
		grupo1.setNome("grupo 1");
		grupo1.setDescricao("grupo de teste 1");
		Grupo grupo2 = new Grupo();
		grupo2.setNome("grupo 2");
		grupo2.setDescricao("grupo de teste 2");

		this.dao.save(grupo1);
		this.dao.save(grupo2);

		List<Grupo> encontrados = this.dao.findAll();

		Assert.assertEquals(2, encontrados.size());
		Assert.assertEquals("grupo 1", encontrados.get(0).getNome());
		Assert.assertEquals("grupo de teste 1", encontrados.get(0).getDescricao());
		Assert.assertEquals("grupo 2", encontrados.get(1).getNome());
		Assert.assertEquals("grupo de teste 2", encontrados.get(1).getDescricao());
	}

}
