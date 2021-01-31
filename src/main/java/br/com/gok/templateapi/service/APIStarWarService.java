package br.com.gok.templateapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gok.templateapi.dto.FilmeDTO;
import br.com.gok.templateapi.dto.PlanetaDTO;

@Service
public class APIStarWarService {

	@Autowired
	private RestTemplate restTemplate;

	public PlanetaDTO[] getPlanetas() throws JsonProcessingException {
		FilmeDTO[] filmes = getFilmes();
		return getPlanetas(filmes);
	}

	private FilmeDTO[] getFilmes() throws JsonProcessingException {
		var jsonFilms = this.restTemplate.getForObject("http://swapi.dev/api/films/", String.class);
		FilmeDTO[] filmes = null;
		do {
			JsonNode filmsRootNode = new ObjectMapper().readTree(jsonFilms);
			if (filmes == null) {
				filmes = new FilmeDTO[filmsRootNode.path("count").asInt()];
			}
			JsonNode filmsResultsNode = filmsRootNode.get("results");
			for (JsonNode filmNode : filmsResultsNode) {
				final int idxFilme = filmNode.path("episode_id").asInt() - 1;
				filmes[idxFilme] = FilmeDTO.builder().titulo(filmNode.path("title").asText().trim()).build();
			}
			jsonFilms = filmsRootNode.path("next").isNull() ? null
					: this.restTemplate.getForObject(filmsRootNode.path("next").asText(), String.class);
		} while (jsonFilms != null);
		return filmes;
	}

	private PlanetaDTO[] getPlanetas(FilmeDTO[] filmes) throws JsonProcessingException {
		PlanetaDTO[] planetas = null;
		int idxPlaneta = 0;
		var jsonPlanets = this.restTemplate.getForObject("http://swapi.dev/api/planets/", String.class);
		do {
			JsonNode planetsRootNode = new ObjectMapper().readTree(jsonPlanets);
			if (planetas == null) {
				planetas = new PlanetaDTO[planetsRootNode.path("count").asInt()];
			}
			JsonNode planetsResultsNode = planetsRootNode.get("results");
			for (JsonNode planetNode : planetsResultsNode) {
				PlanetaDTO planeta = planetas[idxPlaneta++] = PlanetaDTO.builder()
						.nome(planetNode.path("name").asText().trim())
						.clima(planetNode.path("climate").asText())
						.terreno(planetNode.path("terrain").asText())
						.populacao(planetNode.path("population").asText()).build();
				JsonNode filmsLinkNode = planetNode.get("films");
				planeta.setFilmes(new FilmeDTO[filmsLinkNode.size()]);
				int j = 0;
				for (JsonNode filmNode : filmsLinkNode) {
					var jsonFilm = this.restTemplate.getForObject(filmNode.asText(), String.class);
					JsonNode filmRootNode = new ObjectMapper().readTree(jsonFilm);
					int idxFilme = filmRootNode.path("episode_id").asInt() - 1;
					planeta.getFilmes()[j++] = filmes[idxFilme];
				}
			}
			jsonPlanets = planetsRootNode.path("next").isNull() ? null
					: this.restTemplate.getForObject(planetsRootNode.path("next").asText(), String.class);
		} while (jsonPlanets != null);
		return planetas;
	}

}