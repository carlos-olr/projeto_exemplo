package br.com.fatec.projetoweb.core.converter;

import java.util.List;

import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.spektro.minispring.core.implfinder.ImplFinder;
import br.com.spektro.minispring.dto.DTOConverter;

public class UsuarioDTOConverter implements DTOConverter<Usuario, UsuarioDTO> {

	private PapelDTOConverter papelConverter;

	public UsuarioDTOConverter() {
		this.papelConverter = ImplFinder.getFinalImpl(PapelDTOConverter.class);
	}

	@Override
	public UsuarioDTO toDTO(Usuario entidade) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setSenha(entidade.getSenha());
		return dto;
	}

	@Override
	public Usuario toEntity(UsuarioDTO dto) {
		Usuario entidade = new Usuario();
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setSenha(dto.getSenha());
		return entidade;
	}

	@Override
	public List<UsuarioDTO> toDTO(List<Usuario> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> toEntity(List<UsuarioDTO> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
