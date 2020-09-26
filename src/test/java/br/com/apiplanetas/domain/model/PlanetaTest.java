package br.com.apiplanetas.domain.model;

import br.com.apiplanetas.api.mock.MockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Testes do model de Planeta")
public class PlanetaTest {

    @Autowired
    private MockFactory mockFactory;

    private Planeta planeta;

    @Test
    @DisplayName("Testa os getters do model de planeta")
    void deveTestarGetterDoModelPlaneta() {
        planeta = mockFactory.getPlaneta();

        assertAll("planeta",
                () -> assertEquals("Planeta teste", planeta.getNome()),
                () -> assertEquals("Clima teste", planeta.getClima()),
                () -> assertEquals("Terreno teste", planeta.getTerreno()),
                () -> assertEquals("1234566789", planeta.getId())
        );
    }

}
