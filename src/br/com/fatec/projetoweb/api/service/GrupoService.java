package br.com.fatec.projetoweb.api.service;

import java.util.List;

import br.com.fatec.projetoweb.api.dto.GrupoDTO;

public interface GrupoService {

	GrupoDTO salvar(GrupoDTO grupo);

	void atualizar(GrupoDTO grupo);

	void deletar(Long grupoId);

	List<GrupoDTO> listar();

	GrupoDTO buscarPorId(Long grupoId);

}
