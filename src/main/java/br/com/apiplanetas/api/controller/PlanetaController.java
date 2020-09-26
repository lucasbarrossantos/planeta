package br.com.apiplanetas.api.controller;

import br.com.apiplanetas.api.ResourceUriHelper;
import br.com.apiplanetas.api.mapper.PlanetaMapper;
import br.com.apiplanetas.api.model.PlanetaModel;
import br.com.apiplanetas.api.model.input.PlanetaInput;
import br.com.apiplanetas.core.data.PageWrapper;
import br.com.apiplanetas.domain.exception.NegocioException;
import br.com.apiplanetas.domain.exception.PlanetaNaoEncontradoException;
import br.com.apiplanetas.domain.model.Planeta;
import br.com.apiplanetas.domain.repository.PlanetaRepository;
import br.com.apiplanetas.domain.service.PlanetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/planeta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanetaController {

    private final PlanetaService planetaService;
    private final PlanetaRepository planetaRepository;
    private final PlanetaMapper mapper;
    private final PagedResourcesAssembler<Planeta> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<PlanetaModel> listar(Pageable pageable) {
        Page<Planeta> page = planetaRepository.findAll(pageable);
        page = new PageWrapper<>(page, pageable);
        return pagedResourcesAssembler.toModel(page, mapper);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetaModel> getPlanetaPorId(@PathVariable("id") String id) {
        Planeta planeta = planetaService.buscarOuFalhar(id);

        if (planeta != null)
            return ResponseEntity.ok(mapper.toModel(planeta));

        return ResponseEntity.notFound().build();
    }

    @GetMapping(params = "nome")
    public ResponseEntity<List<PlanetaModel>> getPlanetasPorNome(@RequestParam("nome") String nome) {
        List<Planeta> planetas = planetaRepository.findAllByNomeContainingIgnoreCase(nome);

        if (!planetas.isEmpty()) {
            List<PlanetaModel> planetasModel = mapper.toCollectionModel(planetas);
            return ResponseEntity.ok(planetasModel);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PlanetaModel> salvar(@RequestBody @Valid PlanetaInput planetaInput) {
        Planeta planeta = mapper.toDomainObject(planetaInput);
        planeta = planetaService.salvar(planeta);
        ResourceUriHelper.addUriInResponseHeader(planeta.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toModel(planeta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanetaModel> atualizar(@PathVariable("id") String id,
                                                  @RequestBody @Valid PlanetaInput planetaInput) {
        try {
            Planeta planetaDB = planetaService.buscarOuFalhar(id);
            mapper.copyToDomainObject(planetaInput, planetaDB);
            return ResponseEntity.ok(mapper.toModel(planetaService.salvar(planetaDB)));
        } catch (PlanetaNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") String id) {
        planetaService.deleteById(id);
    }

}
