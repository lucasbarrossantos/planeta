package br.com.apiplanetas.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class PlanetaInput {

    @NotBlank
    private String nome;
    @NotBlank
    private String clima;
    @NotBlank
    private String terreno;

}
