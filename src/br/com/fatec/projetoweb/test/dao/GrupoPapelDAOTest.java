
package br.com.fatec.projetoweb.test.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import org.junit.Assert;

import br.com.fatec.projetoweb.api.dao.GrupoPapelDAO;
import br.com.fatec.projetoweb.api.entity.GrupoPapel;
import br.com.fatec.projetoweb.test.comum.TestBase;
import br.com.spektro.minispring.core.implfinder.ImplementationFinder;

public class GrupoPapelDAOTest extends TestBase {

	private GrupoPapelDAO dao;

	@Before
	public void config() {
		this.dao = ImplementationFinder.getImpl(GrupoPapelDAO.class);
	}

	@Test
	public void testSave() {
		GrupoPapel grupo_salvar = new GrupoPapel();
		grupo_salvar.setNome("grupo");
		grupo_salvar.setDescricao("grupo de teste");

		Long id = this.dao.save(grupo_salvar);

		GrupoPapel grupo_salvo = this.dao.findById(id);

		Assert.assertNotNull(grupo_salvo);
		Assert.assertEquals("grupo", grupo_salvo.getNome());
		Assert.assertEquals("grupo de teste", grupo_salvo.getDescricao());
	}

	@Test
	public void testUpdate() {
		GrupoPapel grupo_salvar = new GrupoPapel();
		grupo_salvar.setNome("grupo 1");
		grupo_salvar.setDescricao("grupo de teste");

		Long id = this.dao.save(grupo_salvar);
		GrupoPapel grupo_atualizar = this.dao.findById(id);

		grupo_atualizar.setNome("grupo 1 modificado");
		grupo_atualizar.setDescricao("grupo de teste modificado");

		this.dao.update(grupo_atualizar);
		GrupoPapel grupo_atualizado = this.dao.findById(id);

		Assert.assertNotNull(grupo_atualizado);
		Assert.assertEquals("grupo 1 modificado", grupo_atualizado.getNome());
		Assert.assertEquals("grupo de teste modificado", grupo_atualizado.getDescricao());
	}

	@Test
	public void testDelete() {
		GrupoPapel grupo_salvar = new GrupoPapel();
		grupo_salvar.setNome("grupo");
		grupo_salvar.setDescricao("grupo de teste");

		Long id = this.dao.save(grupo_salvar);
		this.dao.delete(id);

		GrupoPapel grupo_deletado = this.dao.findById(id);

		Assert.assertNull(grupo_deletado);
	}

	@Test
	public void testFindAll() {
		GrupoPapel grupo1 = new GrupoPapel();
		grupo1.setNome("grupo 1");
		grupo1.setDescricao("grupo de teste 1");
		GrupoPapel grupo2 = new GrupoPapel();
		grupo2.setNome("grupo 2");
		grupo2.setDescricao("grupo de teste 2");

		this.dao.save(grupo1);
		this.dao.save(grupo2);

		List<GrupoPapel> encontrados = this.dao.findAll();

		Assert.assertEquals(2, encontrados.size());
		Assert.assertEquals("grupo 1", encontrados.get(0).getNome());
		Assert.assertEquals("grupo de teste 1", encontrados.get(0).getDescricao());
		Assert.assertEquals("grupo 2", encontrados.get(1).getNome());
		Assert.assertEquals("grupo de teste 2", encontrados.get(1).getDescricao());
	}

}
