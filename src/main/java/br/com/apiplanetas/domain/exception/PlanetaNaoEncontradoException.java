package br.com.apiplanetas.domain.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class PlanetaNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PlanetaNaoEncontradoException(MessageSource messageSource, String id) {
        super(String.format(messageSource.getMessage("entidade.id-nao-existe",
                null, LocaleContextHolder.getLocale()), id));
    }

}
