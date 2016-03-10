package br.com.fatec.projetoweb.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetoweb.api.dao.UsuarioDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioGrupoDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioPapelDAO;
import br.com.fatec.projetoweb.api.dto.GrupoPapelDTO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.fatec.projetoweb.api.service.UsuarioService;
import br.com.fatec.projetoweb.core.converter.GrupoPapelDTOConverter;
import br.com.fatec.projetoweb.core.converter.PapelDTOConverter;
import br.com.fatec.projetoweb.core.converter.UsuarioDTOConverter;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class UsuarioServiceImpl implements UsuarioService {

	// DAO
	private UsuarioDAO dao;
	private UsuarioPapelDAO usuarioPapelDAO;
	private UsuarioGrupoDAO usuarioGrupoDAO;

	// Converter
	private UsuarioDTOConverter converter;
	private PapelDTOConverter papelConverter;
	private GrupoPapelDTOConverter grupoPapelConverter;

	public UsuarioServiceImpl() {
		this.dao = ImplFinder.getImpl(UsuarioDAO.class);
		this.usuarioPapelDAO = ImplFinder.getImpl(UsuarioPapelDAO.class);
		this.usuarioGrupoDAO = ImplFinder.getImpl(UsuarioGrupoDAO.class);

		this.converter = ImplFinder.getFinalImpl(UsuarioDTOConverter.class);
		this.papelConverter = ImplFinder.getFinalImpl(PapelDTOConverter.class);
		this.grupoPapelConverter = ImplFinder.getFinalImpl(GrupoPapelDTOConverter.class);
	}

	@Override
	public UsuarioDTO salvar(UsuarioDTO usuario) {
		Long id = this.dao.save(this.converter.toEntity(usuario));
		usuario.setId(id);
		this.atualizarPapeisEGrupos(usuario);
		return this.converter.toDTO(this.dao.findById(id), true);
	}

	@Override
	public UsuarioDTO atualizar(UsuarioDTO usuario) {
		this.dao.update(this.converter.toEntity(usuario));
		this.atualizarPapeisEGrupos(usuario);
		return this.converter.toDTO(this.dao.findById(usuario.getId()), true);
	}

	private void atualizarPapeisEGrupos(UsuarioDTO usuario) {
		this.usuarioPapelDAO.atualizarPapeis(usuario.getId(),
				this.papelConverter.toEntity(usuario.getPapeis()));
		this.usuarioGrupoDAO.atualizarGrupos(usuario.getId(),
				this.grupoPapelConverter.toEntity(usuario.getGrupos()));
	}

	@Override
	public void deletar(Long usuarioId) {
		UsuarioDTO usuario = this.buscarPorId(usuarioId);
		usuario.setGrupos(new ArrayList<GrupoPapelDTO>());
		usuario.setPapeis(new ArrayList<PapelDTO>());
		this.atualizarPapeisEGrupos(usuario);
		this.dao.delete(usuarioId);
	}

	@Override
	public List<UsuarioDTO> listar() {
		return this.converter.toDTOSimples(this.dao.findAll());
	}

	@Override
	public UsuarioDTO buscarPorId(Long usuarioId) {
		Usuario usuario = this.dao.findById(usuarioId);
		return this.converter.toDTO(usuario);
	}

}
