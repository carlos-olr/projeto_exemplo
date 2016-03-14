package br.com.fatec.projetoweb.api.service;

import java.util.List;

import br.com.fatec.projetoweb.api.dto.PapelDTO;

public interface PapelService {

	PapelDTO salvar(PapelDTO papel);

	void atualizar(PapelDTO papel);

	void deletar(Long papelId);

	List<PapelDTO> listar();

	PapelDTO buscarPorId(Long papelId);

}
