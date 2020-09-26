package br.com.apiplanetas.api.controller;

import br.com.apiplanetas.api.mock.MockFactory;
import br.com.apiplanetas.api.model.PlanetaModel;
import br.com.apiplanetas.api.model.input.PlanetaInput;
import br.com.apiplanetas.domain.model.Planeta;
import br.com.apiplanetas.domain.repository.PlanetaRepository;
import br.com.apiplanetas.domain.service.PlanetaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Testes do controller de PlanetaController")
public class PlanetaControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PlanetaService planetaService;

    @MockBean
    private PlanetaRepository planetaRepository;

    @Autowired
    private MockFactory mockFactory;

    @Autowired
    private Gson gson;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Deve salvar um planeta e retornar 201!")
    void deveSalvarUmPlanetaERetornar201() {
        Planeta planetaMock = mockFactory.getPlaneta();
        PlanetaInput planetaInput = mockFactory.getPlanetaInput();

        Mockito.when(planetaService.salvar(Mockito.any(Planeta.class))).thenReturn(planetaMock);
        HttpEntity<PlanetaInput> request = new HttpEntity<>(planetaInput);
        ResponseEntity<String> response = restTemplate.postForEntity("/planeta", request, String.class);
        Planeta planetaResponse = gson.fromJson(response.getBody(), Planeta.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(planetaService, Mockito.times(1)).salvar(Mockito.any(Planeta.class));
        assertEquals(planetaResponse.getId(), "1234566789");
    }

    @Test
    @DisplayName("Deve listar os planetas e retornar 200!")
    void deveListarOsPlanetasERetornar200() throws JsonProcessingException {
        Page<Planeta> pageMock = new PageImpl<>(Collections.singletonList(mockFactory.getPlaneta()));
        PlanetaModel planetaModelMock = mockFactory.getPlanetaModel();

        Mockito.when(planetaRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageMock);
        ResponseEntity<String> response = restTemplate.getForEntity("/planeta", String.class);
        List<PlanetaModel> planetas = mockFactory.getPlanetasFromResponseEntity(response);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(planetas.stream().anyMatch(planeta -> planeta.getId().equals(planetaModelMock.getId())));
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar um planeta que não existe!")
    void deveRetornar404AoTentarBuscarUmPlanetaComCodigoQueNaoExiste() {
        Mockito.when(planetaRepository.findById(Mockito.any(String.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = restTemplate.getForEntity("/planeta/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(planetaService, Mockito.times(1)).buscarOuFalhar("1");
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar um planeta com nome que não existe no Banco de Dados!")
    void deveRetornar404AoTentarBuscarUmPlanetaComNomeQueNaoExiste() {
        Mockito.when(planetaRepository.findAllByNomeContainingIgnoreCase(Mockito.any(String.class)))
                .thenReturn(new ArrayList<>());

        ResponseEntity<String> response = restTemplate.getForEntity("/planeta?nome=teste", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(planetaRepository, Mockito.times(1)).findAllByNomeContainingIgnoreCase("teste");
    }

    @Test
    @DisplayName("Deve excluir um planeta e retornar 204!")
    void deveExcluirUmPlanetaERetornar204() {
        Planeta planetaMock = mockFactory.getPlaneta();

        Mockito.doNothing().when(planetaService).deleteById(planetaMock.getId());

        ResponseEntity<String> response = restTemplate.exchange("/planeta/" + planetaMock.getId(),
                HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(planetaService, Mockito.times(1)).deleteById(planetaMock.getId());
    }

    @Test
    @DisplayName("Deve retornar 400 ao tentar salvar um planeta sem nome!")
    void deveRetornarErroAoTentarSalvarUmPlanetaSemNome() {
        Planeta planetaMock = mockFactory.getPlaneta();
        PlanetaInput planetaInput = mockFactory.getPlanetaInput();
        planetaInput.setNome(null);

        Mockito.when(planetaService.salvar(Mockito.any(Planeta.class))).thenReturn(planetaMock);
        HttpEntity<PlanetaInput> request = new HttpEntity<>(planetaInput);
        ResponseEntity<PlanetaModel> response =
                restTemplate.postForEntity("/planeta", request, PlanetaModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Deve atualizar um planeta e retornar 200!")
    void deveAtualizarUmPlanetaERetornar200() {
        Planeta planetaMock = mockFactory.getPlaneta();

        PlanetaInput planetaInput = mockFactory.getPlanetaInput();
        HttpEntity<PlanetaInput> request = new HttpEntity<>(planetaInput);

        Mockito.when(planetaService.buscarOuFalhar(planetaMock.getId())).thenReturn(planetaMock);
        Mockito.when(planetaService.salvar(Mockito.any(Planeta.class))).thenReturn(planetaMock);

        ResponseEntity<PlanetaModel> response = restTemplate
                .exchange("/planeta/" + planetaMock.getId(), HttpMethod.PUT, request, PlanetaModel.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(planetaMock.getId());
        verify(planetaService, Mockito.times(1)).salvar(planetaMock);
    }

}
