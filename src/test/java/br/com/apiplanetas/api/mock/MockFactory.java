package br.com.apiplanetas.api.mock;

import br.com.apiplanetas.api.model.PlanetaModel;
import br.com.apiplanetas.api.model.input.PlanetaInput;
import br.com.apiplanetas.domain.model.Planeta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockFactory {

    @Autowired
    private ObjectMapper mapper;

    public PlanetaModel getPlanetaModel() {
        PlanetaModel planeta = new PlanetaModel();
        planeta.setNome("Planeta teste");
        planeta.setClima("Clima teste");
        planeta.setTerreno("Terreno teste");
        planeta.setId("1234566789");
        return planeta;
    }

    public PlanetaInput getPlanetaInput() {
        PlanetaInput planeta = new PlanetaInput();
        planeta.setNome("Planeta teste");
        planeta.setClima("Clima teste");
        planeta.setTerreno("Terreno teste");
        return planeta;
    }

    public Planeta getPlaneta() {
        Planeta planeta = new Planeta();
        planeta.setNome("Planeta teste");
        planeta.setClima("Clima teste");
        planeta.setTerreno("Terreno teste");
        planeta.setId("1234566789");
        return planeta;
    }

    public List<PlanetaModel> getPlanetasFromResponseEntity(ResponseEntity<String> response)
            throws JsonProcessingException {

        JsonNode node = mapper.readTree(response.getBody()).get("_embedded");
        return mapper
                .readValue(node.get("planetaModelList").toString(), new TypeReference<List<PlanetaModel>>(){});
    }
}
