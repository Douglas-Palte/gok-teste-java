package br.com.gok.templateapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.gok.templateapi.dto.PlanetaDTO;
import br.com.gok.templateapi.service.APIStarWarService;
import br.com.gok.templateapi.service.FilmeService;
import br.com.gok.templateapi.service.PlanetaService;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableTransactionManagement
public class TemplateApiApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(TemplateApiApplication.class, args);
	}

	@Autowired
	APIStarWarService apiStarWarService;

	@Autowired
	PlanetaService planetaService;

	@Autowired
	FilmeService filmeService;

	private static final Logger logger = LoggerFactory.getLogger(TemplateApiApplication.class);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		new Thread(() -> {
			logger.info("Inicialização: STARTED");
			try {
				if (planetaService.size() == 0) {
					PlanetaDTO[] planetas = apiStarWarService.getPlanetas();
					for (PlanetaDTO planeta : planetas) {
						planetaService.createOrUpdatePlaneta(planeta);
					}
				}
				logger.info("Inicialização: COMPLETA");
			} catch (Exception e) {
				logger.error("Inicialização: FALHOU!!!");
				e.printStackTrace();
			}
		}).start();
	}

}
