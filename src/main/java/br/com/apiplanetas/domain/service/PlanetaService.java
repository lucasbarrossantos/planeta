package br.com.apiplanetas.domain.service;

import br.com.apiplanetas.domain.exception.NegocioException;
import br.com.apiplanetas.domain.exception.PlanetaNaoEncontradoException;
import br.com.apiplanetas.domain.model.Planeta;
import br.com.apiplanetas.domain.repository.PlanetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanetaService {

    private final PlanetaRepository planetaRepository;
    private final MessageSource messageSource;

    public Planeta salvar(Planeta planeta) {
        return planetaRepository.save(planeta);
    }

    public void deleteById(String id) {
        try {
            Planeta planeta = buscarOuFalhar(id);
            planetaRepository.deleteById(planeta.getId());
        } catch (PlanetaNaoEncontradoException e) {
            throw new NegocioException(
                    String.format(messageSource
                            .getMessage("error.planeta-nao-pode-ser-excluido",
                                    null, LocaleContextHolder.getLocale()), id), e);
        }
    }

    public Planeta buscarOuFalhar(String id) {
        return planetaRepository.findById(id)
                .orElseThrow(() -> new PlanetaNaoEncontradoException(messageSource, id));
    }

}
