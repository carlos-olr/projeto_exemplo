
package br.com.fatec.projetoweb.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.fatec.projetoweb.api.dao.GrupoDAO;
import br.com.fatec.projetoweb.api.dao.PapelDAO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.fatec.projetoweb.test.comum.TestBase;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class PapelDAOTest extends TestBase {

	private PapelDAO dao;
	private GrupoDAO grupoDao;
	private Grupo grupo1;
	private Grupo grupo2;

	@Before
	public void config() {
		this.dao = ImplFinder.getImpl(PapelDAO.class);
		this.grupoDao = ImplFinder.getImpl(GrupoDAO.class);

		Grupo g1 = new Grupo();
		g1.setNome("Grupo 1");
		Grupo g2 = new Grupo();
		g2.setNome("Grupo 2");

		Long id1 = this.grupoDao.save(g1);
		Long id2 = this.grupoDao.save(g2);

		this.grupo1 = this.grupoDao.findById(id1);
		this.grupo2 = this.grupoDao.findById(id2);
	}

	@Test
	public void testSave() {
		Papel papel_salvar = new Papel();
		papel_salvar.setNome("papel");
		papel_salvar.setDescricao("papel de teste");
		papel_salvar.setGrupo(this.grupo1);

		Long id = this.dao.save(papel_salvar);

		Papel papel_salvo = this.dao.findById(id);

		Assert.assertNotNull(papel_salvo);
		Assert.assertEquals("papel", papel_salvo.getNome());
		Assert.assertEquals("papel de teste", papel_salvo.getDescricao());
		Assert.assertEquals("Grupo 1", papel_salvo.getGrupo().getNome());
	}

	@Test
	public void testUpdate() {
		Papel papel_salvar = new Papel();
		papel_salvar.setNome("papel 1");
		papel_salvar.setDescricao("papel de teste");
		papel_salvar.setGrupo(this.grupo1);

		Long id = this.dao.save(papel_salvar);
		Papel papel_atualizar = this.dao.findById(id);

		papel_atualizar.setNome("papel 1 modificado");
		papel_atualizar.setDescricao("papel de teste modificado");
		papel_atualizar.setGrupo(this.grupo2);

		this.dao.update(papel_atualizar);
		Papel papel_atualizado = this.dao.findById(id);

		Assert.assertNotNull(papel_atualizado);
		Assert.assertEquals("papel 1 modificado", papel_atualizado.getNome());
		Assert.assertEquals("papel de teste modificado", papel_atualizado.getDescricao());
		Assert.assertEquals("Grupo 2", papel_atualizado.getGrupo().getNome());
	}

	@Test
	public void testDelete() {
		Papel papel_salvar = new Papel();
		papel_salvar.setNome("papel");
		papel_salvar.setDescricao("papel de teste");
		papel_salvar.setGrupo(this.grupo1);

		Long id = this.dao.save(papel_salvar);
		this.dao.delete(id);

		Papel papel_deletado = this.dao.findById(id);

		Assert.assertNull(papel_deletado);
	}

	@Test
	public void testFindAll() {
		Papel papel1 = new Papel();
		papel1.setNome("papel 1");
		papel1.setDescricao("papel de teste 1");
		papel1.setGrupo(this.grupo1);
		Papel papel2 = new Papel();
		papel2.setNome("papel 2");
		papel2.setDescricao("papel de teste 2");
		papel2.setGrupo(this.grupo2);

		this.dao.save(papel1);
		this.dao.save(papel2);

		List<Papel> encontrados = this.dao.findAll();

		Assert.assertEquals(2, encontrados.size());
		Assert.assertEquals("papel 1", encontrados.get(0).getNome());
		Assert.assertEquals("papel de teste 1", encontrados.get(0).getDescricao());
		Assert.assertEquals("Grupo 1", encontrados.get(0).getGrupo().getNome());
		Assert.assertEquals("papel 2", encontrados.get(1).getNome());
		Assert.assertEquals("papel de teste 2", encontrados.get(1).getDescricao());
		Assert.assertEquals("Grupo 2", encontrados.get(1).getGrupo().getNome());
	}

	public void runTests() {
		this.setDown();
		this.config();
		this.testSave();
		this.setDown();
		this.config();
		this.testUpdate();
		this.setDown();
		this.config();
		this.testFindAll();
		this.setDown();
		this.config();
		this.testDelete();
		this.setDown();
	}

}
