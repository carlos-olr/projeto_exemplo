package br.com.fatec.projetoweb.core.converter;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import br.com.fatec.projetoweb.api.dao.GrupoPapelDAO;
import br.com.fatec.projetoweb.api.dao.PapelDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioGrupoDAO;
import br.com.fatec.projetoweb.api.dao.UsuarioPapelDAO;
import br.com.fatec.projetoweb.api.dto.GrupoPapelDTO;
import br.com.fatec.projetoweb.api.dto.PapelDTO;
import br.com.fatec.projetoweb.api.dto.UsuarioDTO;
import br.com.fatec.projetoweb.api.entity.GrupoPapel;
import br.com.fatec.projetoweb.api.entity.Papel;
import br.com.fatec.projetoweb.api.entity.Usuario;
import br.com.spektro.minispring.core.implfinder.ImplFinder;
import br.com.spektro.minispring.dto.DTOConverter;

public class UsuarioDTOConverter implements DTOConverter<Usuario, UsuarioDTO> {

	private UsuarioPapelDAO usuarioPapelDAO;
	private UsuarioGrupoDAO usuarioGrupoDAO;
	private GrupoPapelDAO grupoPapelDAO;
	private PapelDAO papelDAO;
	private PapelDTOConverter papelConverter;
	private GrupoPapelDTOConverter grupoPapelConverter;

	public UsuarioDTOConverter() {
		this.usuarioPapelDAO = ImplFinder.getImpl(UsuarioPapelDAO.class);
		this.usuarioGrupoDAO = ImplFinder.getImpl(UsuarioGrupoDAO.class);
		this.papelConverter = ImplFinder.getFinalImpl(PapelDTOConverter.class);
		this.grupoPapelConverter = ImplFinder.getFinalImpl(GrupoPapelDTOConverter.class);
		this.grupoPapelDAO = ImplFinder.getFinalImpl(GrupoPapelDAO.class);
		this.papelDAO = ImplFinder.getFinalImpl(PapelDAO.class);
	}

	@Override
	public UsuarioDTO toDTO(Usuario entidade) {
		return this.toDTO(entidade, true);
	}

	public UsuarioDTO toDTO(Usuario entidade, boolean conveterDependencias) {
		UsuarioDTO dto = null;
		if (entidade != null) {
			dto = this.toDTOSimples(entidade);
			Long id = dto.getId();
			if (id != null && conveterDependencias) {
				List<Long> idsGrupos = this.usuarioGrupoDAO.buscarGrupos(id);
				List<GrupoPapel> gruposE = this.grupoPapelDAO.findByIds(idsGrupos);
				List<GrupoPapelDTO> gruposDtos = this.grupoPapelConverter.toDTO(gruposE);

				List<Long> idsPapeis = this.usuarioPapelDAO.buscarPapeis(id);
				List<Papel> papeisE = this.papelDAO.findByIds(idsPapeis);
				List<PapelDTO> papeisDtos = this.papelConverter.toDTOSimples(papeisE);

				Set<PapelDTO> papeis = Sets.newLinkedHashSet();
				papeis.addAll(papeisDtos);
				for (Long idGrupo : idsGrupos) {
					List<Papel> papeisGrupo = this.papelDAO.findByGrupo(idGrupo);
					papeis.addAll(this.papelConverter.toDTOSimples(papeisGrupo));
				}

				dto.setGrupos(gruposDtos);
				dto.setPapeis(papeisDtos);
				dto.setPapeisCompilados(papeis);
			}
			dto.setIsAdmin(dto.possuiPapel("ADMIN"));
		}
		return dto;
	}

	public UsuarioDTO toDTOSimples(Usuario entidade) {
		UsuarioDTO dto = new UsuarioDTO();
		Long id = entidade.getId();
		dto.setId(id);
		dto.setNome(entidade.getNome());
		dto.setSenha(entidade.getSenha());
		return dto;
	}

	@Override
	public Usuario toEntity(UsuarioDTO dto) {
		Usuario entidade = new Usuario();
		entidade.setId(dto.getId());
		entidade.setNome(dto.getNome());
		entidade.setSenha(dto.getSenha());
		return entidade;
	}

	@Override
	public List<UsuarioDTO> toDTO(List<Usuario> usuarios) {
		return this.toDTO(usuarios, false);
	}

	public List<UsuarioDTO> toDTOSimples(List<Usuario> usuarios) {
		return this.toDTO(usuarios, true);
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
			dtos.add(isSimples ? this.toDTOSimples(entidade) : this.toDTO(entidade));
		}
		return dtos;
	}

}
