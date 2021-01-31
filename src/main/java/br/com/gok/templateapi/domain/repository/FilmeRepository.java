package br.com.gok.templateapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gok.templateapi.domain.postgres.FilmeModel;

@Repository
public interface FilmeRepository extends JpaRepository<FilmeModel, Long> {

	@Query("SELECT filme FROM FilmeModel filme WHERE LOWER(filme.titulo) = LOWER(:filmeTitulo)")
	FilmeModel findByTitulo(@Param("filmeTitulo") String filmeTitulo);

	List<FilmeModel> findAllByOrderByTitulo();

}
