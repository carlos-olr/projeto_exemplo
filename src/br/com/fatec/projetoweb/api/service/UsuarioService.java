package br.com.fatec.projetoweb.api.service;

import java.util.List;

import br.com.fatec.projetoweb.api.dto.UsuarioDTO;

public interface UsuarioService {

	UsuarioDTO salvar(UsuarioDTO usuario);

	void atualizar(UsuarioDTO usuario);

	void deletar(Long usuarioId);

	List<UsuarioDTO> listar();

	UsuarioDTO buscarPorId(Long usuarioId);

}
