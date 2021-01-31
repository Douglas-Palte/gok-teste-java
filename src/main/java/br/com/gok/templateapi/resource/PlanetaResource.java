package br.com.gok.templateapi.resource;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.gok.templateapi.domain.postgres.PlanetaModel;
import br.com.gok.templateapi.dto.PlanetaDTO;
import br.com.gok.templateapi.exception.NotFoundException;
import br.com.gok.templateapi.service.APIStarWarService;
import br.com.gok.templateapi.service.PlanetaService;

@RequestMapping("/planetas")
@RestController
public class PlanetaResource {

	@Autowired
	PlanetaService planetaService;

	@Autowired
	APIStarWarService apiStarWarService;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<PlanetaModel>> getAllPlanetas() {
		List<PlanetaModel> list = planetaService.getAllPlanetas();
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/apistarwars")
	public ResponseEntity<PlanetaDTO[]> getAllPlanetasStarWars() throws JsonProcessingException {
		PlanetaDTO[] planetas = apiStarWarService.getPlanetas();
		return new ResponseEntity<>(planetas, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/populacao/{minimo}/{maximo}")
	public ResponseEntity<List<PlanetaDTO>> getByPopulacao(@PathVariable("minimo") long minimo, @PathVariable("maximo") long maximo) {
		List<PlanetaModel> models = planetaService.getByPopulacao(minimo, maximo);
		List<PlanetaDTO> dtos = modelMapper.map(models, new TypeToken<List<PlanetaDTO>>() {
		}.getType());
		return new ResponseEntity<>(dtos, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<PlanetaDTO> getPlanetaById(@PathVariable("nome") String nome) throws NotFoundException {
		PlanetaModel model = planetaService.getPlanetaByNome(nome);
		PlanetaDTO dto = modelMapper.map(model, PlanetaDTO.class);
		return new ResponseEntity<>(dto, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PlanetaDTO> getPlanetaById(@PathVariable("id") Long id) throws NotFoundException {
		PlanetaModel model = planetaService.getPlanetaById(id);
		PlanetaDTO dto = modelMapper.map(model, PlanetaDTO.class);
		return new ResponseEntity<>(dto, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PlanetaDTO> createOrUpdatePlaneta(@RequestBody PlanetaDTO planetaDTO) throws NotFoundException {
		PlanetaModel planetaModel = planetaService.createOrUpdatePlaneta(planetaDTO);
		modelMapper.map(planetaModel, planetaDTO);
		return new ResponseEntity<>(planetaDTO, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deletePlanetaById(@PathVariable("id") Long id) throws NotFoundException {
		planetaService.deletePlanetaById(id);
		return HttpStatus.FORBIDDEN;
	}
}