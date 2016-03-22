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
		// Encontrar instâncias das DAOs e dos Conversores através do ImplFinder
		this.usuarioDAO = ImplFinder.getImpl(UsuarioDAO.class);
		this.usuarioPapelDAO = ImplFinder.getImpl(UsuarioPapelDAO.class);
		this.usuarioGrupoDAO = ImplFinder.getImpl(UsuarioGrupoDAO.class);
		// Aqui é usado o método 'getFinalImpl', este não devolve uma
		// implementação da interface, mas sim uam instância da classe passada
		this.usuarioConverter = ImplFinder.getFinalImpl(UsuarioDTOConverter.class);
		this.papelConverter = ImplFinder.getFinalImpl(PapelDTOConverter.class);
		this.GrupoConverter = ImplFinder.getFinalImpl(GrupoDTOConverter.class);
	}

	@Override
	public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
		// usuarioDTO é a instância de UsuarioDTO que deseja ser salva
		// Como os DAOs trabalham com entidades, é necessário converter
		// o DTO para Entidade, para isso serve o método 'toEntity' da classe
		// UsuarioDTOConverter
		Usuario usuarioEntidade = this.usuarioConverter.toEntity(usuarioDTO);
		// Com a entidade já instanciada é possível executar o métododo 'save'
		// da classe UsuarioDAO, e com isso os dados serão escritos no BD.
		Long id = this.usuarioDAO.save(usuarioEntidade);
		// O retorno do método 'save' é o ID gerado para a entidade em questão,
		// este ID será usado para criar as ligações entre as tabelas de
		// Usuario -> Papel e Usuario -> Grupo
		List<PapelDTO> papeis = usuarioDTO.getPapeis();
		List<GrupoDTO> grupos = usuarioDTO.getGrupos();
		// Execução do método que gerencia as ligações entre as tabelas
		this.atualizarPapeisEGrupos(id, papeis, grupos);
		// Por último colocamos o ID gerado em nosso DTO e o devolvemos para
		// quem executou a chamada, neste ponto poderia ser feito um 'findById'
		// mas nesse caso não é necessário
		usuarioDTO.setId(id);
		return usuarioDTO;
	}

	@Override
	public void atualizar(UsuarioDTO usuarioDTO) {
		// Conversão de DTO -> Entidade para assim respeitar as camadas
		Usuario usuarioEntidade = this.usuarioConverter.toEntity(usuarioDTO);
		// Execução do método update para atualizar os dados no BD
		this.usuarioDAO.update(usuarioEntidade);
		List<PapelDTO> papeis = usuarioDTO.getPapeis();
		List<GrupoDTO> grupos = usuarioDTO.getGrupos();
		// Com o "usuário" atualizado, podemos aplicar no BD mudanças em suas
		// relações com Papel e Grupo
		this.atualizarPapeisEGrupos(usuarioDTO.getId(), papeis, grupos);
	}

	private void atualizarPapeisEGrupos(Long usuarioId, List<PapelDTO> papeis,
			List<GrupoDTO> grupos) {
		// Conversão da lista de DTOs para Entidades, isso é necessário porque
		// os DAOs trabalham apenas com entidades
		List<Papel> papeisEntidade = this.papelConverter.toEntity(papeis);
		List<Grupo> gruposEntidade = this.GrupoConverter.toEntity(grupos);
		// Este DAO é responsável pela Ligação Usuario -> Papel
		this.usuarioPapelDAO.atualizarPapeis(usuarioId, papeisEntidade);
		// Este DAO é responsável pela Ligação Usuario -> Grupo
		this.usuarioGrupoDAO.atualizarGrupos(usuarioId, gruposEntidade);
	}

	@Override
	public void deletar(Long usuarioId) {
		// Para deletar um usuário é necessário primeiro remover suas ligações,
		// para que isso ocorra o método 'atualizarPapeisEGrupos' é executado
		// recebando o 'ID' do Usuario alvo junto de listas vazias. Isso é
		// equivalente a remover todos os Papéis e Grupos do Usuário
		this.atualizarPapeisEGrupos(usuarioId, new ArrayList<PapelDTO>(),
				new ArrayList<GrupoDTO>());
		// Após as ligações serem removimas o registro do Usuario por fim é
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
