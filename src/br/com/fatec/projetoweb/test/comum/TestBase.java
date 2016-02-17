
package br.com.fatec.projetoweb.test.comum;

import org.junit.After;
import org.junit.BeforeClass;

import br.com.fatec.projetoweb.api.entity.GrupoPapel;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;
import br.com.spektro.minispring.core.implfinder.ContextSpecifier;
import br.com.spektro.minispring.core.liquibaseRunner.LiquibaseRunnerService;
import br.com.spektro.minispring.core.query.QueryExecutorService;

public class TestBase {

	@BeforeClass
	public static void setUp() {
		ContextSpecifier.setContext("br.com.fatec.projetoweb");
		ConfigDBMapper.setDefaultConnectionName("test");
		LiquibaseRunnerService.run();
	}

	@After
	public void setDown() {
		QueryExecutorService
				.executeQuery("DELETE FROM PROJETO_TESTE_USUARIO_GRUPO_PAPEL");
		QueryExecutorService.executeQuery("DELETE FROM PROJETO_TESTE_USUARIO_PAPEL");
		QueryExecutorService.executeQuery("DELETE FROM " + Papel.TABLE);
		QueryExecutorService.executeQuery("DELETE FROM " + GrupoPapel.TABLE);
		QueryExecutorService.executeQuery("DELETE FROM " + Usuario.TABLE);
	}

}
