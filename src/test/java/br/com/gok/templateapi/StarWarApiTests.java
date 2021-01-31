package br.com.gok.templateapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.gok.templateapi.dto.PlanetaDTO;
import br.com.gok.templateapi.service.APIStarWarService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableFeignClients
public class StarWarApiTests {

	@Autowired
	private APIStarWarService apiStarWarService;

	@Test
	void planetas() throws JsonProcessingException {
		PlanetaDTO[] planetas = apiStarWarService.getPlanetas();
		assertThat(planetas).hasSizeGreaterThan(0);
	}

}
