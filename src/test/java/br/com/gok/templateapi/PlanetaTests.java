package br.com.gok.templateapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.gok.templateapi.domain.postgres.PlanetaModel;
import br.com.gok.templateapi.service.PlanetaService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableFeignClients
class PlanetaTests {

	@Autowired
	private PlanetaService planetaService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void servico() throws JsonMappingException, JsonProcessingException, InterruptedException {
		List<PlanetaModel> planetas = planetaService.getAllPlanetas();
		assertThat(planetas).hasSizeGreaterThan(0);
	}

	@Test
	void endPoint() {
		String body = this.restTemplate.getForObject("/planetas", String.class);
		assertThat(body).contains("Tatooine");
	}

}
