package br.com.fatec.projetoweb.core.service;

import java.util.List;

import br.com.fatec.projetoweb.api.dao.PapelDAO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.fatec.projetoweb.api.service.PapelService;
import br.com.fatec.projetoweb.core.converter.PapelDTOConverter;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class PapelServiceImpl implements PapelService {

	private PapelDAO papelDAO;
	private PapelDTOConverter papelDTOConverter;

	public PapelServiceImpl() {
		this.papelDAO = ImplFinder.getImpl(PapelDAO.class);
		this.papelDTOConverter = ImplFinder.getFinalImpl(PapelDTOConverter.class);
	}

	@Override
	public PapelDTO salvar(PapelDTO papelDTO) {
		Papel papel = this.papelDTOConverter.toEntity(papelDTO);
		Long id = this.papelDAO.save(papel);
		papelDTO.setId(id);
		return papelDTO;
	}

	@Override
	public void atualizar(PapelDTO papelDTO) {
		Papel papel = this.papelDTOConverter.toEntity(papelDTO);
		this.papelDAO.update(papel);
	}

	@Override
	public void deletar(Long papelId) {
		this.papelDAO.delete(papelId);
	}

	@Override
	public List<PapelDTO> listar() {
		return this.papelDTOConverter.toDTO(this.papelDAO.findAll());
	}

	@Override
	public PapelDTO buscarPorId(Long papelId) {
		return this.papelDTOConverter.toDTO(this.papelDAO.findById(papelId));
	}

}
