package br.com.fatec.projetoweb.core.service;

import java.util.List;

import br.com.fatec.projetoweb.api.dao.GrupoDAO;
import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.fatec.projetoweb.api.service.GrupoService;
import br.com.fatec.projetoweb.core.converter.GrupoDTOConverter;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class GrupoServiceImpl implements GrupoService {

	private GrupoDAO grupoDAO;
	private GrupoDTOConverter grupoDTOConverter;

	public GrupoServiceImpl() {
		this.grupoDAO = ImplFinder.getImpl(GrupoDAO.class);
		this.grupoDTOConverter = ImplFinder.getFinalImpl(GrupoDTOConverter.class);
	}

	@Override
	public GrupoDTO salvar(GrupoDTO grupoDTO) {
		Grupo grupoEntidade = this.grupoDTOConverter.toEntity(grupoDTO);
		Long id = this.grupoDAO.save(grupoEntidade);
		grupoDTO.setId(id);
		return grupoDTO;
	}

	@Override
	public void atualizar(GrupoDTO grupoDTO) {
		Grupo grupoEntidade = this.grupoDTOConverter.toEntity(grupoDTO);
		this.grupoDAO.update(grupoEntidade);
	}

	@Override
	public void deletar(Long grupoId) {
		this.grupoDAO.delete(grupoId);
	}

	@Override
	public List<GrupoDTO> listar() {
		return this.grupoDTOConverter.toDTO(this.grupoDAO.findAll());
	}

	@Override
	public GrupoDTO buscarPorId(Long grupoId) {
		return this.grupoDTOConverter.toDTO(this.grupoDAO.findById(grupoId));
	}

}
