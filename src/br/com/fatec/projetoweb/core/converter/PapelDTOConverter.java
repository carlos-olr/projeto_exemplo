package br.com.fatec.projetoweb.core.converter;

import java.util.List;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.entity.GrupoPapel;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.spektro.minispring.core.implfinder.ImplFinder;
import br.com.spektro.minispring.dto.DTOConverter;

public class PapelDTOConverter implements DTOConverter<Papel, PapelDTO> {

	private GrupoPapelDTOConverter grupoPapelDTOConverter;

	public PapelDTOConverter() {
		this.grupoPapelDTOConverter = ImplFinder
				.getFinalImpl(GrupoPapelDTOConverter.class);
	}

	@Override
	public PapelDTO toDTO(Papel entidade) {
		PapelDTO dto = new PapelDTO();
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setDescricao(entidade.getDescricao());
		if (entidade.getGrupo() != null) {
			dto.setGrupo(this.grupoPapelDTOConverter.toDTO(entidade.getGrupo()));
		}
		return dto;
	}

	@Override
	public Papel toEntity(PapelDTO dto) {
		Papel entidade = new Papel();
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setDescricao(dto.getDescricao());
		if (dto.getGrupo() != null) {
			entidade.setGrupo(new GrupoPapel(dto.getGrupo().getId()));
		}
		return entidade;
	}

	public List<Papel> toEntity(List<PapelDTO> dtos) {
		List<Papel> papeis = Lists.newArrayList();
		for (PapelDTO dto : dtos) {
			papeis.add(this.toEntity(dto));
		}
		return papeis;
	}

	public List<PapelDTO> toDTO(List<Papel> entidades) {
		List<PapelDTO> papeis = Lists.newArrayList();
		for (Papel entidade : entidades) {
			papeis.add(this.toDTO(entidade));
		}
		return papeis;
	}

}
