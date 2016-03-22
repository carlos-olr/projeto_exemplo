package br.com.fatec.projetoweb.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetoweb.api.dao.UsuarioDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioGrupoDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioPapelDAO;
import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.fatec.projetoweb.api.service.UsuarioService;
import br.com.fatec.projetoweb.core.converter.GrupoDTOConverter;
import br.com.fatec.projetoweb.core.converter.PapelDTOConverter;
import br.com.fatec.projetoweb.core.converter.UsuarioDTOConverter;
import br.com.spektro.minispring.core.implfinder.ImplFinder;

public class UsuarioServiceImpl implements UsuarioService {

	// DAO
	private UsuarioDAO usuarioDAO;
	private UsuarioPapelDAO usuarioPapelDAO;
	private UsuarioGrupoDAO usuarioGrupoDAO;

	// Converter
	private UsuarioDTOConverter usuarioConverter;
	private PapelDTOConverter papelConverter;
	private GrupoDTOConverter GrupoConverter;

	public UsuarioServiceImpl() {
		// Encontrar inst�ncias das DAOs e dos Conversores atrav�s do ImplFinder
		this.usuarioDAO = ImplFinder.getImpl(UsuarioDAO.class);
		this.usuarioPapelDAO = ImplFinder.getImpl(UsuarioPapelDAO.class);
		this.usuarioGrupoDAO = ImplFinder.getImpl(UsuarioGrupoDAO.class);
		// Aqui � usado o m�todo 'getFinalImpl', este n�o devolve uma
		// implementa��o da interface, mas sim uam inst�ncia da classe passada
		this.usuarioConverter = ImplFinder.getFinalImpl(UsuarioDTOConverter.class);
		this.papelConverter = ImplFinder.getFinalImpl(PapelDTOConverter.class);
		this.GrupoConverter = ImplFinder.getFinalImpl(GrupoDTOConverter.class);
	}

	@Override
	public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
		// usuarioDTO � a inst�ncia de UsuarioDTO que deseja ser salva
		// Como os DAOs trabalham com entidades, � necess�rio converter
		// o DTO para Entidade, para isso serve o m�todo 'toEntity' da classe
		// UsuarioDTOConverter
		Usuario usuarioEntidade = this.usuarioConverter.toEntity(usuarioDTO);
		// Com a entidade j� instanciada � poss�vel executar o m�tododo 'save'
		// da classe UsuarioDAO, e com isso os dados ser�o escritos no BD.
		Long id = this.usuarioDAO.save(usuarioEntidade);
		// O retorno do m�todo 'save' � o ID gerado para a entidade em quest�o,
		// este ID ser� usado para criar as liga��es entre as tabelas de
		// Usuario -> Papel e Usuario -> Grupo
		List<PapelDTO> papeis = usuarioDTO.getPapeis();
		List<GrupoDTO> grupos = usuarioDTO.getGrupos();
		// Execu��o do m�todo que gerencia as liga��es entre as tabelas
		this.atualizarPapeisEGrupos(id, papeis, grupos);
		// Por �ltimo colocamos o ID gerado em nosso DTO e o devolvemos para
		// quem executou a chamada, neste ponto poderia ser feito um 'findById'
		// mas nesse caso n�o � necess�rio
		usuarioDTO.setId(id);
		return usuarioDTO;
	}

	@Override
	public void atualizar(UsuarioDTO usuarioDTO) {
		// Convers�o de DTO -> Entidade para assim respeitar as camadas
		Usuario usuarioEntidade = this.usuarioConverter.toEntity(usuarioDTO);
		// Execu��o do m�todo update para atualizar os dados no BD
		this.usuarioDAO.update(usuarioEntidade);
		List<PapelDTO> papeis = usuarioDTO.getPapeis();
		List<GrupoDTO> grupos = usuarioDTO.getGrupos();
		// Com o "usu�rio" atualizado, podemos aplicar no BD mudan�as em suas
		// rela��es com Papel e Grupo
		this.atualizarPapeisEGrupos(usuarioDTO.getId(), papeis, grupos);
	}

	private void atualizarPapeisEGrupos(Long usuarioId, List<PapelDTO> papeis,
			List<GrupoDTO> grupos) {
		// Convers�o da lista de DTOs para Entidades, isso � necess�rio porque
		// os DAOs trabalham apenas com entidades
		List<Papel> papeisEntidade = this.papelConverter.toEntity(papeis);
		List<Grupo> gruposEntidade = this.GrupoConverter.toEntity(grupos);
		// Este DAO � respons�vel pela Liga��o Usuario -> Papel
		this.usuarioPapelDAO.atualizarPapeis(usuarioId, papeisEntidade);
		// Este DAO � respons�vel pela Liga��o Usuario -> Grupo
		this.usuarioGrupoDAO.atualizarGrupos(usuarioId, gruposEntidade);
	}

	@Override
	public void deletar(Long usuarioId) {
		// Para deletar um usu�rio � necess�rio primeiro remover suas liga��es,
		// para que isso ocorra o m�todo 'atualizarPapeisEGrupos' � executado
		// recebando o 'ID' do Usuario alvo junto de listas vazias. Isso �
		// equivalente a remover todos os Pap�is e Grupos do Usu�rio
		this.atualizarPapeisEGrupos(usuarioId, new ArrayList<PapelDTO>(),
				new ArrayList<GrupoDTO>());
		// Ap�s as liga��es serem removimas o registro do Usuario por fim �
		// eliminado do BD
		this.usuarioDAO.delete(usuarioId);
	}

	@Override
	public List<UsuarioDTO> listar() {
		List<Usuario> usuarios = this.usuarioDAO.findAll();
		return this.usuarioConverter.toDTOSimples(usuarios);
	}

	@Override
	public UsuarioDTO buscarPorId(Long usuarioId) {
		Usuario usuario = this.usuarioDAO.findById(usuarioId);
		return this.usuarioConverter.toDTO(usuario);
	}

	@Override
	public UsuarioDTO buscarPorLoginESenha(String login, String senha) {
		Usuario usuario = this.usuarioDAO.findByLoginAndPassword(login, senha);
		UsuarioDTO dto = null;
		if (usuario != null) {
			dto = this.usuarioConverter.toDTOSimples(usuario);
		}
		return dto;
	}

}
