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
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class PlanetaDTO {
	@NonNull
	private String nome;
	@NonNull
	private String clima;
	@NonNull
	private String terreno;
	@NonNull
	private String populacao;

	@Builder.Default
	@NonNull
	private FilmeDTO[] filmes = new FilmeDTO[0];
}
