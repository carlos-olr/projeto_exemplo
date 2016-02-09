package br.com.fatec.projetoweb.api.dao;

import java.util.List;

import br.com.fatec.projetoweb.api.entity.GrupoPapel;

public interface UsuarioGrupoDAO {

	public void atualizarGrupos(Long usuarioId, List<GrupoPapel> grupos);

	public List<Long> buscarGrupos(Long usuarioId);

}
