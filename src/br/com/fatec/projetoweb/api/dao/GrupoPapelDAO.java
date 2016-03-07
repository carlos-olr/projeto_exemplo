
package br.com.fatec.projetoweb.api.dao;

import java.util.List;

import br.com.fatec.projetoweb.api.entity.GrupoPapel;

public interface GrupoPapelDAO {

	Long save(GrupoPapel grupoPapel);

	void update(GrupoPapel grupoPapel);

	void delete(Long id);

	GrupoPapel findById(Long id);

	List<GrupoPapel> findAll();

	List<GrupoPapel> findByIds(List<Long> ids);

}
