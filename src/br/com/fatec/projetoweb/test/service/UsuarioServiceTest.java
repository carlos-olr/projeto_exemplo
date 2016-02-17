package br.com.fatec.projetoweb.test.service;

import org.junit.Test;

import br.com.fatec.projetoweb.api.service.UsuarioService;
import br.com.fatec.projetoweb.test.comum.TestBase;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class UsuarioServiceTest extends TestBase {

	@Test
	public void blah() {
		UsuarioService impl = ImplFinder.getImpl(UsuarioService.class);
		System.out.println(impl);
	}

}
