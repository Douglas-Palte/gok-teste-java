package br.com.gok.templateapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gok.templateapi.domain.postgres.PlanetaModel;

@Repository
public interface PlanetaRepository extends JpaRepository<PlanetaModel, Long> {

	@Query("SELECT planeta FROM PlanetaModel planeta WHERE LOWER(planeta.nome) = LOWER(:planetaNome)")
	PlanetaModel findByNome(@Param("planetaNome") String planetaNome);

	List<PlanetaModel> findAllByOrderByNome();

	@Query("SELECT planeta FROM PlanetaModel planeta WHERE planeta.populacao <> 'unknown' AND CAST(planeta.populacao AS long) between :minimo AND :maximo")
	List<PlanetaModel> findByPopulacao(@Param("minimo") long minimo, @Param("maximo") long maximo);

}
