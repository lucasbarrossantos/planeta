package br.com.apiplanetas.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class PlanetaModel extends RepresentationModel<PlanetaModel> {

    private String id;
    private String nome;
    private String clima;
    private String terreno;

}
