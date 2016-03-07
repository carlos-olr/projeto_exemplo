package br.com.fatec.projetoweb.test.comum;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import br.com.fatec.projetoweb.api.dao.GrupoPapelDAO;
import br.com.fatec.projetoweb.api.dao.PapelDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioGrupoDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioPapelDAO;
import br.com.fatec.projetoweb.api.dto.GrupoPapelDTO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.entity.GrupoPapel;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.fatec.projetoweb.api.service.GrupoPapelService;
import br.com.fatec.projetoweb.api.service.PapelService;
import br.com.fatec.projetoweb.api.service.UsuarioService;
import br.com.fatec.projetoweb.core.converter.GrupoPapelDTOConverter;
import br.com.fatec.projetoweb.core.converter.PapelDTOConverter;
import br.com.fatec.projetoweb.core.converter.UsuarioDTOConverter;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public abstract class TestCenario extends TestBase {

	protected UsuarioDAO usuarioDAO;
	protected PapelDAO papelDAO;
	protected GrupoPapelDAO grupoDAO;
	protected UsuarioGrupoDAO usuarioGrupoDAO;
	protected UsuarioPapelDAO usuarioPapelDAO;

	protected PapelDTOConverter papelConverter;
	protected UsuarioDTOConverter usuarioConverter;
	protected GrupoPapelDTOConverter grupoConverter;

	protected UsuarioService usuarioService;
	protected PapelService papelService;
	protected GrupoPapelService grupoService;

	protected Map<Long, GrupoPapel> grupos = Maps.newHashMap();
	protected Map<Long, Papel> papeis = Maps.newHashMap();
	protected Map<Long, Usuario> usuarios = Maps.newHashMap();

	protected Map<Long, GrupoPapelDTO> grupoDtos = Maps.newHashMap();
	protected Map<Long, PapelDTO> papelDtos = Maps.newHashMap();
	protected Map<Long, UsuarioDTO> usuarioDtos = Maps.newHashMap();

	@Before
	public void upCenario() {
		this.papelDAO = ImplFinder.getImpl(PapelDAO.class);
		this.usuarioDAO = ImplFinder.getImpl(UsuarioDAO.class);
		this.grupoDAO = ImplFinder.getImpl(GrupoPapelDAO.class);
		this.usuarioGrupoDAO = ImplFinder.getImpl(UsuarioGrupoDAO.class);
		this.usuarioPapelDAO = ImplFinder.getImpl(UsuarioPapelDAO.class);

		this.papelConverter = ImplFinder.getFinalImpl(PapelDTOConverter.class);
		this.usuarioConverter = ImplFinder.getFinalImpl(UsuarioDTOConverter.class);
		this.grupoConverter = ImplFinder.getFinalImpl(GrupoPapelDTOConverter.class);

		this.papelService = ImplFinder.getImpl(PapelService.class);
		this.usuarioService = ImplFinder.getImpl(UsuarioService.class);
		this.grupoService = ImplFinder.getImpl(GrupoPapelService.class);

		this.criarGrupos();
		this.criarPapeis();
		this.criarUsuarios();
	}

	@After
	public void downCenario() {
		this.grupos = Maps.newHashMap();
		this.papeis = Maps.newHashMap();
		this.usuarios = Maps.newHashMap();
	}

	// Criação de Cenários
	private void criarUsuarios() {
		Usuario u1 = new Usuario(1l, "Carlos", "blah1");
		Usuario u2 = new Usuario(2l, "Augusto", "blah2");
		Usuario u3 = new Usuario(3l, "Argemiro", "blah3");

		Long u1Id = this.usuarioDAO.save(u1);
		this.usuarios.put(u1Id, this.usuarioDAO.findById(1l));
		Long u2Id = this.usuarioDAO.save(u2);
		this.usuarios.put(u2Id, this.usuarioDAO.findById(2l));
		Long u3Id = this.usuarioDAO.save(u3);
		this.usuarios.put(u3Id, this.usuarioDAO.findById(3l));

		this.usuarioDtos.put(1l, this.usuarioConverter.toDTO(this.usuarios.get(1l)));
		this.usuarioDtos.put(2l, this.usuarioConverter.toDTO(this.usuarios.get(2l)));
		this.usuarioDtos.put(3l, this.usuarioConverter.toDTO(this.usuarios.get(3l)));
	}

	private void criarPapeis() {
		Papel p1 = new Papel(1l, "ADMIN", "Administrador do Sistema", null);
		Papel p2 = new Papel(2l, "CRUD_CONTAB", "Acessar Cadastros da Contabilidade",
				this.grupos.get(1l));
		Papel p3 = new Papel(3l, "CRUD_RH", "Acessar Cadastros do RH",
				this.grupos.get(2l));
		Papel p4 = new Papel(4l, "CRUD_TI", "Acessar Cadastros do TI",
				this.grupos.get(3l));
		Papel p5 = new Papel(5l, "REL_CONTAB", "Relatórios Contáveis",
				this.grupos.get(1l));
		Papel p6 = new Papel(6l, "RH_DADOS_CRI", "Acessar dados críticos de RH",
				this.grupos.get(2l));
		Papel p7 = new Papel(7l, "REL_TI", "Relatórios da TI", this.grupos.get(3l));
		Papel p8 = new Papel(8l, "REABRIR_CHAMADOS", "Pode Reabrir Chamados",
				this.grupos.get(3l));

		Long p1Id = this.papelDAO.save(p1);
		this.papeis.put(p1Id, this.papelDAO.findById(p1Id));
		Long p2Id = this.papelDAO.save(p2);
		this.papeis.put(p2Id, this.papelDAO.findById(p2Id));
		Long p3Id = this.papelDAO.save(p3);
		this.papeis.put(p3Id, this.papelDAO.findById(p3Id));
		Long p4Id = this.papelDAO.save(p4);
		this.papeis.put(p4Id, this.papelDAO.findById(p4Id));
		Long p5Id = this.papelDAO.save(p5);
		this.papeis.put(p5Id, this.papelDAO.findById(p5Id));
		Long p6Id = this.papelDAO.save(p6);
		this.papeis.put(p6Id, this.papelDAO.findById(p6Id));
		Long p7Id = this.papelDAO.save(p7);
		this.papeis.put(p7Id, this.papelDAO.findById(p7Id));
		Long p8Id = this.papelDAO.save(p8);
		this.papeis.put(p8Id, this.papelDAO.findById(p8Id));

		this.papelDtos.put(1l, this.papelConverter.toDTOSimples(this.papeis.get(1l)));
		this.papelDtos.put(2l, this.papelConverter.toDTOSimples(this.papeis.get(2l)));
		this.papelDtos.put(3l, this.papelConverter.toDTOSimples(this.papeis.get(3l)));
		this.papelDtos.put(4l, this.papelConverter.toDTOSimples(this.papeis.get(4l)));
		this.papelDtos.put(5l, this.papelConverter.toDTOSimples(this.papeis.get(5l)));
		this.papelDtos.put(6l, this.papelConverter.toDTOSimples(this.papeis.get(6l)));
		this.papelDtos.put(7l, this.papelConverter.toDTOSimples(this.papeis.get(7l)));
		this.papelDtos.put(8l, this.papelConverter.toDTOSimples(this.papeis.get(8l)));
	}

	private void criarGrupos() {
		GrupoPapel g1 = new GrupoPapel(1l, "Contabilidade", "Acesso Contábil");
		GrupoPapel g2 = new GrupoPapel(2l, "Recursos Humanos", "Acesso ao RH");
		GrupoPapel g3 = new GrupoPapel(3l, "Informática", "Acesso TI");

		Long g1Id = this.grupoDAO.save(g1);
		this.grupos.put(g1Id, this.grupoDAO.findById(g1Id));
		Long g2Id = this.grupoDAO.save(g2);
		this.grupos.put(g2Id, this.grupoDAO.findById(g2Id));
		Long g3Id = this.grupoDAO.save(g3);
		this.grupos.put(g3Id, this.grupoDAO.findById(g3Id));

		this.grupoDtos.put(1l, this.grupoConverter.toDTO(this.grupos.get(1l)));
		this.grupoDtos.put(2l, this.grupoConverter.toDTO(this.grupos.get(2l)));
		this.grupoDtos.put(3l, this.grupoConverter.toDTO(this.grupos.get(3l)));
	}

	// Métodos auxiliares

	protected List<PapelDTO> getPapeis(Long... ids) {
		List<PapelDTO> papeis = Lists.newArrayList();
		for (Long id : ids) {
			papeis.add(this.papelDtos.get(id));
		}
		return papeis;
	}

	protected List<GrupoPapelDTO> getGrupos(Long... ids) {
		List<GrupoPapelDTO> grupos = Lists.newArrayList();
		for (Long id : ids) {
			grupos.add(this.grupoDtos.get(id));
		}
		return grupos;
	}
}
