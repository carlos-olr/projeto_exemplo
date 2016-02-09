
package br.com.fatec.projetoweb.api.dao;

import java.util.List;

import br.com.fatec.projetoweb.api.entity.Papel;

public interface PapelDAO {

	Long save(Papel papel);

	void update(Papel papel);

	void delete(Long id);

	Papel findById(Long id);

	List<Papel> findAll();

	List<Papel> findByIds(List<Long> ids);

}
