package br.com.fatec.projetoweb.core.converter;

import java.util.List;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.spektro.minispring.core.implfinder.ImplFinder;
import br.com.spektro.minispring.dto.DTOConverter;

public class PapelDTOConverter implements DTOConverter<Papel, PapelDTO> {

	private GrupoDTOConverter GrupoDTOConverter;

	public PapelDTOConverter() {
		this.GrupoDTOConverter = ImplFinder.getFinalImpl(GrupoDTOConverter.class);
	}

	@Override
	public PapelDTO toDTO(Papel entidade) {
		PapelDTO dto = this.toDTOSimples(entidade);
		if (entidade.getGrupo() != null) {
			dto.setGrupo(this.GrupoDTOConverter.toDTO(entidade.getGrupo()));
		}
		return dto;
	}

	public PapelDTO toDTOSimples(Papel entidade) {
		PapelDTO dto = new PapelDTO();
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setDescricao(entidade.getDescricao());
		return dto;
	}

	@Override
	public Papel toEntity(PapelDTO dto) {
		Papel entidade = new Papel();
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setDescricao(dto.getDescricao());
		if (dto.getGrupo() != null) {
			entidade.setGrupo(new Grupo(dto.getGrupo().getId()));
		}
		return entidade;
	}

	@Override
	public List<Papel> toEntity(List<PapelDTO> dtos) {
		List<Papel> papeis = Lists.newArrayList();
		for (PapelDTO dto : dtos) {
			papeis.add(this.toEntity(dto));
		}
		return papeis;
	}

	@Override
	public List<PapelDTO> toDTO(List<Papel> entidades) {
		return this.toDTO(entidades, false);
	}

	public List<PapelDTO> toDTOSimples(List<Papel> entidades) {
		return this.toDTO(entidades, true);
	}

	private List<PapelDTO> toDTO(List<Papel> entidades, boolean isSimples) {
		List<PapelDTO> papeis = Lists.newArrayList();
		for (Papel entidade : entidades) {
			papeis.add(isSimples ? this.toDTOSimples(entidade) : this.toDTO(entidade));
		}
		return papeis;
	}
}
