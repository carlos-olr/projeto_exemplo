package br.com.fatec.projetoweb.core.dao;

import static br.com.spektro.minispring.core.dbmapper.ConfigDBMapper.getDefaultConnectionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dao.GrupoDAO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;

public class GrupoDAOImpl implements GrupoDAO {

	@Override

	public Long save(Grupo grupo) {
		Connection conn = null;
		PreparedStatement insert = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();

			String colunas = DAOUtils.getColunas(
					ConfigDBMapper.getDefaultConnectionType(), Grupo.getColunas());

			String values = DAOUtils.completarClausulaValues(getDefaultConnectionType(),
					2, "SEQ_PROJETO_GRUPO_PAPEL");

			String sql = "INSERT INTO " + Grupo.TABLE + colunas + " VALUES "
					+ values;

			insert = DAOUtils.criarStatment(sql, conn, getDefaultConnectionType(),
					Grupo.getColunasArray());

			insert.setString(1, grupo.getNome());
			insert.setString(2, grupo.getDescricao());
			insert.executeUpdate();

			ResultSet generatedKeys = insert.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			}

			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(insert);
			DbUtils.closeQuietly(conn);
		}
	}

	@Override
	public void update(Grupo grupo) {
		Connection conn = null;
		PreparedStatement update = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			update = conn.prepareStatement("UPDATE " + Grupo.TABLE + " SET "
					+ Grupo.COL_NOME + " = ?, " + Grupo.COL_DESCRICAO + " = ? "
					+ " WHERE " + Grupo.COL_ID + " = ?");
			update.setString(1, grupo.getNome());
			update.setString(2, grupo.getDescricao());
			update.setLong(3, grupo.getId());
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
			String sql = "DELETE FROM " + Grupo.TABLE + " WHERE ID = ?";
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
	public Grupo findById(Long id) {
		Connection conn = null;
		PreparedStatement find = null;
		Grupo user = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "SELECT * FROM " + Grupo.TABLE + " WHERE "
					+ Grupo.COL_ID + " = ?";
			find = conn.prepareStatement(sql);
			find.setLong(1, id);
			ResultSet rs = find.executeQuery();
			if (rs.next()) {
				user = this.buildGrupo(rs);
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
	public List<Grupo> findAll() {
		Connection conn = null;
		PreparedStatement findAll = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			findAll = conn.prepareStatement("SELECT * FROM " + Grupo.TABLE);
			ResultSet rs = findAll.executeQuery();
			return this.buildGrupos(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(findAll);
		}
	}

	private List<Grupo> buildGrupos(ResultSet rs) throws SQLException {
		List<Grupo> gruposPapel = Lists.newArrayList();
		while (rs.next()) {
			gruposPapel.add(this.buildGrupo(rs));
		}
		return gruposPapel;
	}

	private Grupo buildGrupo(ResultSet rs) throws SQLException {
		Grupo grupo = new Grupo();
		grupo.setId(rs.getLong(Grupo.COL_ID));
		grupo.setNome(rs.getString(Grupo.COL_NOME));
		grupo.setDescricao(rs.getString(Grupo.COL_DESCRICAO));
		return grupo;
	}

	@Override
	public List<Grupo> findByIds(List<Long> ids) {
		List<Grupo> grupos = Lists.newArrayList();
		if (ids.size() > 0) {
			Connection conn = null;
			PreparedStatement stmt = null;
			try {
				conn = ConfigDBMapper.getDefaultConnection();
				String args = DAOUtils.preparePlaceHolders(ids.size());
				String sql = "SELECT * FROM " + Grupo.TABLE + " WHERE "
						+ Grupo.COL_ID + " IN (" + args + ")";
				stmt = conn.prepareStatement(sql);
				DAOUtils.setValues(stmt, ids);
				ResultSet rs = stmt.executeQuery();
				grupos = this.buildGrupos(rs);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				DbUtils.closeQuietly(conn);
				DbUtils.closeQuietly(stmt);
			}
		}
		return grupos;
	}

}
