package br.com.apiplanetas.api.mapper;

import br.com.apiplanetas.api.controller.PlanetaController;
import br.com.apiplanetas.api.model.PlanetaModel;
import br.com.apiplanetas.api.model.input.PlanetaInput;
import br.com.apiplanetas.domain.model.Planeta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanetaMapper extends
        RepresentationModelAssemblerSupport<Planeta, PlanetaModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PlanetaMapper() {
        super(PlanetaController.class, PlanetaModel.class);
    }

    public Planeta toDomainObject(PlanetaInput planetaInput) {
        return modelMapper.map(planetaInput, Planeta.class);
    }

    public void copyToDomainObject(PlanetaInput planetaInput, Planeta planeta) {
        modelMapper.map(planetaInput, planeta);
    }

    public PlanetaModel toModel(Planeta planeta) {
        PlanetaModel pedidoModel = createModelWithId(planeta.getId(), planeta);
        return modelMapper.map(planeta, PlanetaModel.class);
    }

    public List<PlanetaModel> toCollectionModel(List<Planeta> planetas) {
        return planetas
                .stream()
                .map(this::toModel).collect(Collectors.toList());
    }

}
