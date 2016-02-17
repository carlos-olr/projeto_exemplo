package br.com.fatec.projetoweb.core.service;

import java.util.List;

import br.com.fatec.projetoweb.api.dao.UsuarioDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioGrupoDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioPapelDAO;
import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
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
		return this.converter.toDTO(this.dao.findById(id));
	}

	@Override
	public UsuarioDTO atualizar(UsuarioDTO usuario) {
		this.dao.update(this.converter.toEntity(usuario));
		this.atualizarPapeisEGrupos(usuario);
		return this.converter.toDTO(this.dao.findById(usuario.getId()));
	}

	private void atualizarPapeisEGrupos(UsuarioDTO usuario) {
		this.usuarioPapelDAO.atualizarPapeis(usuario.getId(),
				this.papelConverter.toEntity(usuario.getPapeis()));
		this.usuarioGrupoDAO.atualizarGrupos(usuario.getId(),
				this.grupoPapelConverter.toEntity(usuario.getGrupos()));
	}

	@Override
	public void deletar(Long usuarioId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UsuarioDTO> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioDTO buscarPorId(Long usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}

}
