package br.com.fatec.projetoweb.core.dao;

import static br.com.spektro.minispring.core.dbmapper.ConfigDBMapper.getDefaultConnectionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dao.GrupoDAO;
import br.com.fatec.projetoweb.api.dao.PapelDAO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class PapelDAOImpl implements PapelDAO {

	private GrupoDAO grupoDao;

	public PapelDAOImpl() {
		this.grupoDao = ImplFinder.getImpl(GrupoDAO.class);
	}

	@Override
	public Long save(Papel papel) {
		Connection conn = null;
		PreparedStatement insert = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();

			String colunas = DAOUtils.getColunas(getDefaultConnectionType(),
					Papel.getColunas());

			String values = DAOUtils.completarClausulaValues(getDefaultConnectionType(),
					3, "SEQ_PROJETO_PAPEL");

			String sql = "INSERT INTO " + Papel.TABLE + colunas + " VALUES " + values;

			insert = DAOUtils.criarStatment(sql, conn, getDefaultConnectionType(),
					Papel.getColunasArray());

			insert.setString(1, papel.getNome());
			insert.setString(2, papel.getDescricao());
			Grupo grupo = papel.getGrupo();
			if (grupo != null) {
				insert.setLong(3, papel.getGrupo().getId());
			} else {
				insert.setNull(3, Types.BIGINT);
			}
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
	public void update(Papel papel) {
		Connection conn = null;
		PreparedStatement update = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			List<String> sets = Lists.newArrayList();
			sets.add(Papel.COL_NOME + " = ?");
			sets.add(Papel.COL_DESCRICAO + " = ?");
			sets.add(Papel.COL_GRUPO_ID + " = ?");

			String sql = "UPDATE " + Papel.TABLE + " SET " + StringUtils.join(sets, ", ")
					+ " WHERE " + Papel.COL_ID + " = ?";

			update = conn.prepareStatement(sql);
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
			String sql = "DELETE FROM " + Papel.TABLE + " WHERE " + Papel.COL_ID + " = ?";
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
			String sql = "SELECT * FROM " + Papel.TABLE + " WHERE " + Papel.COL_ID
					+ " = ?";
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
			findAll = conn.prepareStatement(
					"SELECT * FROM " + Papel.TABLE + " ORDER BY " + Papel.COL_ID);
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
				String args = DAOUtils.preparePlaceHolders(ids.size());
				String sql = "SELECT * FROM " + Papel.TABLE + " WHERE ID IN (" + args
						+ ") ORDER BY " + Papel.COL_ID + ";";
				stmt = conn.prepareStatement(sql);
				DAOUtils.setValues(stmt, ids);
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

	@Override
	public List<Papel> findByGrupo(Long idGrupo) {
		List<Papel> papeis = Lists.newArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConfigDBMapper.getDefaultConnection();
			String sql = "SELECT * FROM " + Papel.TABLE + " WHERE " + Papel.COL_GRUPO_ID
					+ " = ? ORDER BY " + Papel.COL_ID + ";";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, idGrupo);
			ResultSet rs = stmt.executeQuery();
			papeis = this.buildPapeis(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(stmt);
		}
		return papeis;
	}

}
