package br.com.gok.templateapi.domain.postgres;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity()
@Table(name = "filme")
public class FilmeModel {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	private String titulo;

}
