package br.com.apiplanetas.domain.repository;

import br.com.apiplanetas.domain.model.Planeta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, String> {

    List<Planeta> findAllByNomeContainingIgnoreCase(String nome);

}
