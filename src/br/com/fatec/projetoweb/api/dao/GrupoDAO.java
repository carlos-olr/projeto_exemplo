
package br.com.fatec.projetoweb.api.dao;

import java.util.List;

import br.com.fatec.projetoweb.api.entity.Grupo;

public interface GrupoDAO {

	Long save(Grupo Grupo);

	void update(Grupo Grupo);

	void delete(Long id);

	Grupo findById(Long id);

	List<Grupo> findAll();

	List<Grupo> findByIds(List<Long> ids);

}
