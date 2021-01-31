package br.com.gok.templateapi.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gok.templateapi.dto.TemplateDTO;
import io.swagger.v3.core.util.Json;

@RequestMapping("/api/template/v1")
@RestController
public class TemplateResource {

	@GetMapping("/healtcheck")
	public String templateHealtCheck() {
		TemplateDTO template = TemplateDTO.builder().isActive(true).build();
		return Json.pretty(template);
	}
}