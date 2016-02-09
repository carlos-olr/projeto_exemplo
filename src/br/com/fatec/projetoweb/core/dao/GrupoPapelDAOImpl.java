package br.com.fatec.projetoweb.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dao.GrupoPapelDAO;
import br.com.fatec.projetoweb.api.entity.GrupoPapel;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;
import br.com.spektro.minispring.core.query.GeradorIdService;

public class GrupoPapelDAOImpl implements GrupoPapelDAO {

	@Override

	public Long save(GrupoPapel grupo) {
		Connection conn = null;
		PreparedStatement insert = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "INSERT INTO " + GrupoPapel.TABLE + " VALUES (?,?,?);";
			insert = conn.prepareStatement(sql);
			Long id = GeradorIdService.getNextId(GrupoPapel.TABLE);

			insert.setLong(1, id);
			insert.setString(2, grupo.getNome());
			insert.setString(3, grupo.getDescricao());
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
	public void update(GrupoPapel grupo) {
		Connection conn = null;
		PreparedStatement update = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			update = conn.prepareStatement("UPDATE " + GrupoPapel.TABLE + " SET " + GrupoPapel.COL_NOME + " = ?, "
					+ GrupoPapel.COL_DESCRICAO + " = ? " + " WHERE " + GrupoPapel.COL_ID + " = ?");
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
			String sql = "DELETE FROM " + GrupoPapel.TABLE + " WHERE ID = ?;";
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
	public GrupoPapel findById(Long id) {
		Connection conn = null;
		PreparedStatement find = null;
		GrupoPapel user = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "SELECT * FROM " + GrupoPapel.TABLE + " WHERE " + GrupoPapel.COL_ID + " = ?;";
			find = conn.prepareStatement(sql);
			find.setLong(1, id);
			ResultSet rs = find.executeQuery();
			if (rs.next()) {
				user = this.buildGrupoPapel(rs);
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
	public List<GrupoPapel> findAll() {
		Connection conn = null;
		PreparedStatement findAll = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			findAll = conn.prepareStatement("SELECT * FROM " + GrupoPapel.TABLE);
			ResultSet rs = findAll.executeQuery();
			return this.buildGrupoPapels(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(findAll);
		}
	}

	private List<GrupoPapel> buildGrupoPapels(ResultSet rs) throws SQLException {
		List<GrupoPapel> gruposPapel = Lists.newArrayList();
		while (rs.next()) {
			gruposPapel.add(this.buildGrupoPapel(rs));
		}
		return gruposPapel;
	}

	private GrupoPapel buildGrupoPapel(ResultSet rs) throws SQLException {
		GrupoPapel grupo = new GrupoPapel();
		grupo.setId(rs.getLong(GrupoPapel.COL_ID));
		grupo.setNome(rs.getString(GrupoPapel.COL_NOME));
		grupo.setDescricao(rs.getString(GrupoPapel.COL_DESCRICAO));
		return grupo;
	}

}
