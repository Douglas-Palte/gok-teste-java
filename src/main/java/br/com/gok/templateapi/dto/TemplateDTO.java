package br.com.gok.templateapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(value = Include.NON_NULL)
public class TemplateDTO {
	private Boolean isActive;
}
