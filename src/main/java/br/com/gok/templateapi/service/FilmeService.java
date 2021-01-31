package br.com.gok.templateapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gok.templateapi.domain.postgres.FilmeModel;
import br.com.gok.templateapi.domain.repository.FilmeRepository;

@Service
public class FilmeService {

	@Autowired
	FilmeRepository repository;

	public FilmeModel createIfNotExistsFilme(FilmeModel newEntity) {
		FilmeModel model = repository.findByTitulo(newEntity.getTitulo());
		if (model != null) {
			return model;
		} else {
			return repository.save(newEntity);
		}
	}

}