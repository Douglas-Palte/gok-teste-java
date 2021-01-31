package br.com.gok.templateapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class FilmeDTO {

	@NonNull
	private String titulo;
}
