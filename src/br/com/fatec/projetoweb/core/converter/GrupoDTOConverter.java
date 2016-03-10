package br.com.fatec.projetoweb.core.converter;

import java.util.List;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.spektro.minispring.dto.DTOConverter;

public class GrupoDTOConverter implements DTOConverter<Grupo, GrupoDTO> {

	@Override
	public GrupoDTO toDTO(Grupo entidade) {
		GrupoDTO dto = new GrupoDTO();
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setDescricao(entidade.getDescricao());
		return dto;
	}

	@Override
	public Grupo toEntity(GrupoDTO dto) {
		Grupo entidade = new Grupo();
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setDescricao(dto.getDescricao());
		return entidade;
	}

	@Override
	public List<GrupoDTO> toDTO(List<Grupo> entidades) {
		List<GrupoDTO> dtos = Lists.newArrayList();
		for (Grupo entidade : entidades) {
			dtos.add(this.toDTO(entidade));
		}
		return dtos;
	}

	@Override
	public List<Grupo> toEntity(List<GrupoDTO> dtos) {
		List<Grupo> entidades = Lists.newArrayList();
		for (GrupoDTO dto : dtos) {
			entidades.add(this.toEntity(dto));
		}
		return entidades;
	}

}
