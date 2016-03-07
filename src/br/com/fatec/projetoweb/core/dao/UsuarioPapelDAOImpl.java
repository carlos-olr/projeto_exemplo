package br.com.fatec.projetoweb.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dao.UsuarioPapelDAO;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;

public class UsuarioPapelDAOImpl implements UsuarioPapelDAO {

	private static final String TABLE = "PROJETO_USUARIO_PAPEL";
	private static final String COL_ID_PAPEL = "ID_PAPEL";
	private static final String COL_ID_USUARIO = "ID_USUARIO";

	@Override
	public void atualizarPapeis(Long usuarioId, List<Papel> papeis) {
		if (papeis != null) {
			papeis.removeAll(Collections.singleton(null));
			Connection connection = ConfigDBMapper.getDefaultConnection();
			PreparedStatement delete = null;
			PreparedStatement insert = null;
			try {
				String sqlD = "DELETE FROM " + TABLE + " WHERE ID_USUARIO = ?";
				delete = connection.prepareStatement(sqlD);
				delete.setLong(1, usuarioId);
				delete.execute();

				for (Papel papel : papeis) {
					String sqlI = "INSERT INTO " + TABLE + " VALUES (?,?)";
					insert = connection.prepareStatement(sqlI);
					insert.setLong(1, usuarioId);
					insert.setLong(2, papel.getId());
					insert.execute();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				DbUtils.closeQuietly(insert);
				DbUtils.closeQuietly(delete);
				DbUtils.closeQuietly(connection);
			}
		}
	}

	@Override
	public List<Long> buscarPapeis(Long usuarioId) {
		List<Long> papeis = Lists.newArrayList();
		if (usuarioId != null) {
			Connection connection = ConfigDBMapper.getDefaultConnection();
			PreparedStatement select = null;
			try {
				String sql = "SELECT " + COL_ID_PAPEL + " FROM " + TABLE + " WHERE "
						+ COL_ID_USUARIO + " = ?;";
				select = connection.prepareStatement(sql);
				select.setLong(1, usuarioId);
				ResultSet resultSet = select.executeQuery();
				while (resultSet.next()) {
					papeis.add(resultSet.getLong(COL_ID_PAPEL));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return papeis;
	}

}
