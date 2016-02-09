package br.com.fatec.projetoweb.api.dao;

import java.util.List;

import br.com.fatec.projetoweb.api.entity.Papel;

public interface UsuarioPapelDAO {

	public void atualizarPapeis(Long usuarioId, List<Papel> papeis);

	public List<Long> buscarPapeis(Long usuarioId);

}