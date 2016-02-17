package br.com.fatec.projetoweb.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.google.common.collect.Lists;

import br.com.fatec.projetoweb.api.dao.UsuarioGrupoDAO;
import br.com.fatec.projetoweb.api.entity.GrupoPapel;
import br.com.spektro.minispring.core.dbmapper.ConfigDBMapper;

public class UsuarioGrupoDAOImpl implements UsuarioGrupoDAO {

	private static final String TABLE = "PROJETO_TESTE_USUARIO_GRUPO";
	private static final String COL_ID_GRUPO = "ID_GRUPO";
	private static final String COL_ID_USUARIO = "ID_USUARIO";

	@Override
	public void atualizarGrupos(Long usuarioId, List<GrupoPapel> grupos) {
		if (grupos != null && !grupos.isEmpty()) {
			grupos.removeAll(Collections.singleton(null));
			Connection connection = ConfigDBMapper.getDefaultConnection();
			PreparedStatement delete = null;
			PreparedStatement insert = null;
			try {
				String sqlD = "DELETE FROM " + TABLE + " WHERE " + COL_ID_USUARIO
						+ " = ?";
				delete = connection.prepareStatement(sqlD);
				delete.setLong(1, usuarioId);
				delete.execute();
				delete.close();

				for (GrupoPapel grupo : grupos) {
					String sqlI = "INSERT INTO " + TABLE + " VALUES (?,?)";
					insert = connection.prepareStatement(sqlI);
					insert.setLong(1, usuarioId);
					insert.setLong(2, grupo.getId());
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
	public List<Long> buscarGrupos(Long usuarioId) {
		List<Long> grupos = Lists.newArrayList();
		if (usuarioId != null) {
			Connection connection = ConfigDBMapper.getDefaultConnection();
			PreparedStatement select = null;
			try {
				String sql = "SELECT " + COL_ID_GRUPO + " FROM " + TABLE + " WHERE "
						+ COL_ID_USUARIO + " = ?;";
				select = connection.prepareStatement(sql);
				select.setLong(1, usuarioId);
				ResultSet resultSet = select.executeQuery();
				while (resultSet.next()) {
					grupos.add(resultSet.getLong(COL_ID_GRUPO));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return grupos;
	}

}
