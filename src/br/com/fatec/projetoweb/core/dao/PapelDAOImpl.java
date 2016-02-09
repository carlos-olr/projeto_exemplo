package br.com.fatec.projetoweb.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dao.GrupoPapelDAO;
import br.com.fatec.projetoweb.api.dao.PapelDAO;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;
import br.com.spektro.minispring.core.implfinder.ImplementationFinder;
import br.com.spektro.minispring.core.query.GeradorIdService;

public class PapelDAOImpl implements PapelDAO {

	private GrupoPapelDAO grupoDao;

	public PapelDAOImpl() {
		this.grupoDao = ImplementationFinder.getImpl(GrupoPapelDAO.class);
	}

	@Override
	public Long save(Papel papel) {
		Connection conn = null;
		PreparedStatement insert = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "INSERT INTO " + Papel.TABLE + " VALUES (?,?,?,?);";
			insert = conn.prepareStatement(sql);
			Long id = GeradorIdService.getNextId(Papel.TABLE);

			insert.setLong(1, id);
			insert.setString(2, papel.getNome());
			insert.setString(3, papel.getDescricao());
			insert.setLong(4, papel.getGrupo().getId());
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
	public void update(Papel papel) {
		Connection conn = null;
		PreparedStatement update = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			update = conn.prepareStatement(
					"UPDATE " + Papel.TABLE + " SET " + Papel.COL_NOME + " = ?, " + Papel.COL_DESCRICAO + " = ?, "
							+ Papel.COL_GRUPO_ID + " = ? " + " WHERE " + Papel.COL_ID + " = ?");
			update.setString(1, papel.getNome());
			update.setString(2, papel.getDescricao());
			update.setLong(3, papel.getGrupo().getId());
			update.setLong(4, papel.getId());
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
			String sql = "DELETE FROM " + Papel.TABLE + " WHERE ID = ?;";
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
	public Papel findById(Long id) {
		Connection conn = null;
		PreparedStatement find = null;
		Papel user = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "SELECT * FROM " + Papel.TABLE + " WHERE " + Papel.COL_ID + " = ?;";
			find = conn.prepareStatement(sql);
			find.setLong(1, id);
			ResultSet rs = find.executeQuery();
			if (rs.next()) {
				user = this.buildPapel(rs);
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
	public List<Papel> findAll() {
		Connection conn = null;
		PreparedStatement findAll = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			findAll = conn.prepareStatement("SELECT * FROM " + Papel.TABLE);
			ResultSet rs = findAll.executeQuery();
			return this.buildPapeis(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(findAll);
		}
	}

	private List<Papel> buildPapeis(ResultSet rs) throws SQLException {
		List<Papel> papeis = Lists.newArrayList();
		while (rs.next()) {
			papeis.add(this.buildPapel(rs));
		}
		return papeis;
	}

	private Papel buildPapel(ResultSet rs) throws SQLException {
		Papel papel = new Papel();
		papel.setId(rs.getLong(Papel.COL_ID));
		papel.setNome(rs.getString(Papel.COL_NOME));
		papel.setDescricao(rs.getString(Papel.COL_DESCRICAO));
		papel.setGrupo(this.grupoDao.findById(rs.getLong(Papel.COL_GRUPO_ID)));
		return papel;
	}

	@Override
	public List<Papel> findByIds(List<Long> ids) {
		List<Papel> papeis = Lists.newArrayList();
		if (ids.size() > 0) {
			Connection conn = null;
			PreparedStatement stmt = null;
			try {
				conn = ConfigDBMapper.getDefaultConnection();
				String args = this.preparePlaceHolders(ids.size());
				String sql = "SELECT * FROM " + Papel.TABLE + " WHERE ID IN (" + args + ");";
				stmt = conn.prepareStatement(sql);
				this.setValues(stmt, ids);
				ResultSet rs = stmt.executeQuery();
				papeis = this.buildPapeis(rs);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				DbUtils.closeQuietly(conn);
				DbUtils.closeQuietly(stmt);
			}
		}
		return papeis;
	}

	private String preparePlaceHolders(int length) {
		return StringUtils.join(Collections.nCopies(length, "?"), ",");
	}

	private void setValues(PreparedStatement preparedStatement, List<Long> valore) throws SQLException {
		for (int i = 0; i < valore.size(); i++) {
			preparedStatement.setObject(i + 1, valore.get(i));
		}
	}

}
