package br.com.fatec.projetoweb.core.converter;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import br.com.fatec.projetoweb.api.dao.GrupoDAO;
import br.com.fatec.projetoweb.api.dao.PapelDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioGrupoDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioPapelDAO;
import br.com.fatec.projetoweb.api.dto.GrupoDTO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.entity.Grupo;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.spektro.minispring.core.implfinder.ImplFinder;
import br.com.spektro.minispring.dto.DTOConverter;

public class UsuarioDTOConverter implements DTOConverter<Usuario, UsuarioDTO> {

	private GrupoDAO GrupoDAO;
	private PapelDAO papelDAO;
	private UsuarioPapelDAO usuarioPapelDAO;
	private UsuarioGrupoDAO usuarioGrupoDAO;
	private PapelDTOConverter papelConverter;
	private GrupoDTOConverter GrupoConverter;

	public UsuarioDTOConverter() {
		this.usuarioPapelDAO = ImplFinder.getImpl(UsuarioPapelDAO.class);
		this.usuarioGrupoDAO = ImplFinder.getImpl(UsuarioGrupoDAO.class);
		this.papelConverter = ImplFinder.getFinalImpl(PapelDTOConverter.class);
		this.GrupoConverter = ImplFinder.getFinalImpl(GrupoDTOConverter.class);
		this.GrupoDAO = ImplFinder.getImpl(GrupoDAO.class);
		this.papelDAO = ImplFinder.getImpl(PapelDAO.class);
	}

	@Override
	public UsuarioDTO toDTO(Usuario entidade) {
		return this.toDTO(entidade, true);
	}

	public UsuarioDTO toDTO(Usuario entidade, boolean conveterDependencias) {
		// A geração do DTO sempre inicia com a conversão simples
		UsuarioDTO dto = this.toDTOSimples(entidade);
		Long id = entidade.getId();
		// A entidade possuir um 'ID' demostra que a mesma já está salva,
		// e somente assim pode ter ligação com outras entidades
		if (id != null && conveterDependencias) {
			// Primeiro é gerado uma lista de todos os IDs de Grupos que possuem
			// ligações com o Usuario
			List<Long> idsGrupos = this.usuarioGrupoDAO.buscarGrupos(id);
			// Em seguida é feita uma busca considerando todos os IDs passados
			List<Grupo> gruposEntidade = this.GrupoDAO.findByIds(idsGrupos);
			// A lista de entidades que possuem esses IDs é gerada e então
			// convertida para uma lista de DTOs
			List<GrupoDTO> gruposDTO = this.GrupoConverter.toDTO(gruposEntidade);

			// O mesmo processo feio para os Grupos é feito para os Papéis
			List<Long> idsPapeis = this.usuarioPapelDAO.buscarPapeis(id);
			List<Papel> papeisEntidade = this.papelDAO.findByIds(idsPapeis);
			List<PapelDTO> papeisDTO = this.papelConverter.toDTOSimples(papeisEntidade);

			// A classe Usuario DTO possui um atributo chamado 'papeisUsuario'
			// que é uma compilação de todos os papeis ligados ao Usuario,
			// diretamente e através de um Grupo. Essa compilação é gerada
			// através do Set 'papeisUsuario'
			Set<PapelDTO> papeisUsuario = Sets.newLinkedHashSet();
			// Primeiro adicionamos todos os Papeis diretamente ligados ao Set
			papeisUsuario.addAll(papeisDTO);
			// Então é feito um LOOP para todos os Grupos ligados ao Usuario
			for (Long idGrupo : idsGrupos) {
				// Para cada Grupo é feita uma busca de Papeis que perteçam a
				// este Grupo
				List<Papel> papeisGrupo = this.papelDAO.findByGrupo(idGrupo);
				// O retorno da busca é convertido em DTO e adicionado ao Set
				papeisUsuario.addAll(this.papelConverter.toDTOSimples(papeisGrupo));
			}

			// E os atributos são "SETADOS"
			dto.setGrupos(gruposDTO);
			dto.setPapeis(papeisDTO);
			dto.setPapeisUsuario(papeisUsuario);

			// Por último é verificado se o Usuario possui a Role ADMIN
			dto.setIsAdmin(dto.possuiPapel("ADMIN"));
		}
		return dto;
	}

	public UsuarioDTO toDTOSimples(Usuario entidade) {
		// A conversão simples serve apenas converter os atributos básicos da
		// Entidade, utilize esse sistema de conversão simples em casos em que
		// o DTO possuir dependências para outros DTOs.
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(entidade.getId());
		dto.setNome(entidade.getNome());
		dto.setSenha(entidade.getSenha());
		dto.setLogin(entidade.getLogin());
		return dto;
	}

	@Override
	public Usuario toEntity(UsuarioDTO dto) {
		// Na conversão sentido Entidade somente os atributos da Entidade ALVO
		// são necessários. Se analisarmos a classe UsuarioDTO veremos muitos
		// outros atributos que não dizem respeito a Entidade Usuario
		Usuario entidade = new Usuario();
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setSenha(dto.getSenha());
		entidade.setLogin(dto.getLogin());
		return entidade;
	}

	@Override
	public List<UsuarioDTO> toDTO(List<Usuario> usuarios) {
		return this.toDTO(usuarios, true);
	}

	public List<UsuarioDTO> toDTOSimples(List<Usuario> usuarios) {
		return this.toDTO(usuarios, false);
	}

	@Override
	public List<Usuario> toEntity(List<UsuarioDTO> dtos) {
		List<Usuario> entidades = Lists.newArrayList();
		for (UsuarioDTO dto : dtos) {
			entidades.add(this.toEntity(dto));
		}
		return entidades;
	}

	private List<UsuarioDTO> toDTO(List<Usuario> entidades, boolean isSimples) {
		List<UsuarioDTO> dtos = Lists.newArrayList();
		for (Usuario entidade : entidades) {
			dtos.add(this.toDTO(entidade, isSimples));
		}
		return dtos;
	}

}
