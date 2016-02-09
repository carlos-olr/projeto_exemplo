package br.com.fatec.projetoweb.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dao.UsuarioDAO;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;
import br.com.spektro.minispring.core.query.GeradorIdService;

public class UsuarioDAOImpl implements UsuarioDAO {

	@Override

	public Long save(Usuario usuario) {
		Connection conn = null;
		PreparedStatement insert = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "INSERT INTO " + Usuario.TABLE + " VALUES (?,?,?);";
			insert = conn.prepareStatement(sql);
			Long id = GeradorIdService.getNextId(Usuario.TABLE);

			insert.setLong(1, id);
			insert.setString(2, usuario.getNome());
			insert.setString(3, usuario.getSenha());
			insert.execute();

			return id;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(insert);
			DbUtils.closeQuietly(conn);
		}
	}

	@Override
	public void update(Usuario usuario) {
		Connection conn = null;
		PreparedStatement update = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			update = conn.prepareStatement("UPDATE " + Usuario.TABLE + " SET " + Usuario.COL_NOME + " = ?, "
					+ Usuario.COL_SENHA + " = ? " + " WHERE " + Usuario.COL_ID + " = ?");
			update.setString(1, usuario.getNome());
			update.setString(2, usuario.getSenha());
			update.setLong(3, usuario.getId());
			update.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(update);
		}
	}

	@Override
	public void delete(Long id) {
		Connection conn = null;
		PreparedStatement delete = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "DELETE FROM " + Usuario.TABLE + " WHERE ID = ?;";
			delete = conn.prepareStatement(sql);
			delete.setLong(1, id);
			delete.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(delete);
			DbUtils.closeQuietly(conn);
		}
	}

	@Override
	public Usuario findById(Long id) {
		Connection conn = null;
		PreparedStatement find = null;
		Usuario user = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "SELECT * FROM " + Usuario.TABLE + " WHERE " + Usuario.COL_ID + " = ?;";
			find = conn.prepareStatement(sql);
			find.setLong(1, id);
			ResultSet rs = find.executeQuery();
			if (rs.next()) {
				user = this.buildUsuario(rs);
			}
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(find);
		}
	}

	@Override
	public List<Usuario> findAll() {
		Connection conn = null;
		PreparedStatement findAll = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			findAll = conn.prepareStatement("SELECT * FROM " + Usuario.TABLE);
			ResultSet rs = findAll.executeQuery();
			return this.buildUsuarios(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(findAll);
		}
	}

	private List<Usuario> buildUsuarios(ResultSet rs) throws SQLException {
		List<Usuario> usuarios = Lists.newArrayList();
		while (rs.next()) {
			usuarios.add(this.buildUsuario(rs));
		}
		return usuarios;
	}

	private Usuario buildUsuario(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getLong(Usuario.COL_ID));
		usuario.setNome(rs.getString(Usuario.COL_NOME));
		usuario.setSenha(rs.getString(Usuario.COL_SENHA));
		return usuario;
	}

}
