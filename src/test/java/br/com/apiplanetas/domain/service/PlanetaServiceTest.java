package br.com.apiplanetas.domain.service;

import br.com.apiplanetas.api.mock.MockFactory;
import br.com.apiplanetas.domain.model.Planeta;
import br.com.apiplanetas.domain.repository.PlanetaRepository;
import br.com.apiplanetas.domain.service.PlanetaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Testes do service de PlanetaService")
public class PlanetaServiceTest {

    @InjectMocks
    private PlanetaService planetaService;

    @Mock
    private PlanetaRepository planetaRepository;

    @Autowired
    private MockFactory mockFactory;

    @Test
    @DisplayName("Deve salvar um planeta e retornar com sucesso!")
    void deveSalvarUmPlanetaComSucesso() {
        Planeta planetaMock = mockFactory.getPlaneta();

        Mockito.when(planetaRepository.save(Mockito.any(Planeta.class))).thenReturn(planetaMock);

        planetaMock = planetaService.salvar(planetaMock);

        assertEquals(planetaMock.getId(), "1234566789");
        verify(planetaRepository, Mockito.times(1)).save(Mockito.any(Planeta.class));
        assertThat(planetaMock.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve excluir um planeta com sucesso!")
    void deveExcluirUmPlanetaComSucesso() {
        String id = mockFactory.getPlaneta().getId();

        Mockito.doNothing().when(planetaRepository).deleteById(id);

        planetaService.deleteById(id);

        verify(planetaRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve buscar um planeta por id com sucesso!")
    void deveBuscarUmPlanetaPorIdComSucesso() {
        String id = mockFactory.getPlaneta().getId();
        Optional<Planeta> planetaMock = Optional.of(mockFactory.getPlaneta());

        Mockito.when(planetaRepository.findById(id)).thenReturn(planetaMock);

        Planeta planeta = planetaService.buscarOuFalhar(id);

        verify(planetaRepository, Mockito.times(1)).findById(id);
        assertThat(planeta.getId()).isNotNull();
        assertEquals(planeta.getId(), "1234566789");
    }

}
