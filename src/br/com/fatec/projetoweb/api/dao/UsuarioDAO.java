
package br.com.fatec.projetoweb.api.dao;

import java.util.List;

import br.com.fatec.projetoweb.api.entity.Usuario;

public interface UsuarioDAO {

	Long save(Usuario usuario);

	void update(Usuario usuario);

	void delete(Long id);

	Usuario findById(Long id);

	List<Usuario> findAll();

	Usuario findByLoginAndPassword(String login, String senha);

}
