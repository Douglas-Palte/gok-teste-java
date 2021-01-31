package br.com.gok.templateapi.domain.postgres;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity()
@Table(name = "planeta")
public class PlanetaModel {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	private String nome;

	@Column(nullable = false)
	private String clima;

	@Column(nullable = false)
	private String terreno;

	@Column(nullable = false)
	private String populacao;

	@ManyToMany
	@JoinTable(name = "filme_planeta", joinColumns = @JoinColumn(name = "planeta_id"), inverseJoinColumns = @JoinColumn(name = "filme_id"))
	private Set<FilmeModel> filmes = new HashSet<>();

}
