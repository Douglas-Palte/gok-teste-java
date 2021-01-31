package br.com.gok.templateapi.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gok.templateapi.domain.postgres.FilmeModel;
import br.com.gok.templateapi.domain.postgres.PlanetaModel;
import br.com.gok.templateapi.domain.repository.PlanetaRepository;
import br.com.gok.templateapi.dto.PlanetaDTO;
import br.com.gok.templateapi.exception.NotFoundException;

@Service
public class PlanetaService {

	@Autowired
	PlanetaRepository repository;

	@Autowired
	FilmeService filmeService;

	@Autowired
	private ModelMapper modelMapper;

	public List<PlanetaModel> getAllPlanetas() {
		List<PlanetaModel> planetaList = repository.findAllByOrderByNome();
		if (!planetaList.isEmpty()) {
			return planetaList;
		} else {
			return new ArrayList<>();
		}
	}

	public List<PlanetaModel> getByPopulacao(long minimo, long maximo) {
		List<PlanetaModel> planetaList = repository.findByPopulacao(minimo, maximo);
		if (!planetaList.isEmpty()) {
			return planetaList;
		} else {
			return new ArrayList<>();
		}
	}

	public PlanetaModel getPlanetaByNome(String nome) throws NotFoundException {
		PlanetaModel planeta = repository.findByNome(nome);
		if (planeta != null) {
			return planeta;
		} else {
			throw new NotFoundException("No planeta record exist for given nome");
		}
	}

	public PlanetaModel getPlanetaById(Long id) throws NotFoundException {
		Optional<PlanetaModel> planeta = repository.findById(id);
		if (planeta.isPresent()) {
			return planeta.get();
		} else {
			throw new NotFoundException("No planeta record exist for given id");
		}
	}

	public PlanetaModel createOrUpdatePlaneta(PlanetaDTO planetaDTO) throws NotFoundException {
		PlanetaModel planetaModel = repository.findByNome(planetaDTO.getNome());
		if (planetaModel != null) {
			modelMapper.map(planetaDTO, planetaModel);
		} else {
			planetaModel = modelMapper.map(planetaDTO, PlanetaModel.class);
		}
		Set<FilmeModel> filmes = new HashSet<>();
		for (FilmeModel filmeModel : planetaModel.getFilmes()) {
			filmes.add(filmeService.createIfNotExistsFilme(filmeModel));
		}
		planetaModel.getFilmes().clear();
		planetaModel = repository.save(planetaModel);
		planetaModel.setFilmes(filmes);
		return repository.save(planetaModel);
	}

	public void deletePlanetaById(Long id) throws NotFoundException {
		Optional<PlanetaModel> planeta = repository.findById(id);
		if (planeta.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new NotFoundException("No planeta record exist for given id");
		}
	}

	public long size() {
		return repository.count();
	}

}