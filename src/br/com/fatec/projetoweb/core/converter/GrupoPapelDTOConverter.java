package br.com.fatec.projetoweb.core.converter;

import java.util.List;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dto.GrupoPapelDTO;
import br.com.fatec.projetoweb.api.entity.GrupoPapel;
import br.com.spektro.minispring.dto.DTOConverter;

public class GrupoPapelDTOConverter implements DTOConverter<GrupoPapel, GrupoPapelDTO> {

	@Override
	public GrupoPapelDTO toDTO(GrupoPapel entidade) {
		GrupoPapelDTO dto = new GrupoPapelDTO();
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setDescricao(entidade.getDescricao());
		return dto;
	}

	@Override
	public GrupoPapel toEntity(GrupoPapelDTO dto) {
		GrupoPapel entidade = new GrupoPapel();
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setDescricao(dto.getDescricao());
		return entidade;
	}

	@Override
	public List<GrupoPapelDTO> toDTO(List<GrupoPapel> entidades) {
		List<GrupoPapelDTO> dtos = Lists.newArrayList();
		for (GrupoPapel entidade : entidades) {
			dtos.add(this.toDTO(entidade));
		}
		return dtos;
	}

	@Override
	public List<GrupoPapel> toEntity(List<GrupoPapelDTO> dtos) {
		List<GrupoPapel> entidades = Lists.newArrayList();
		for (GrupoPapelDTO dto : dtos) {
			entidades.add(this.toEntity(dto));
		}
		return entidades;
	}

}
