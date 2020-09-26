package br.com.apiplanetas.domain.exception;

public class PlanetaNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PlanetaNaoEncontradoException(String id) {
        super(String.format("Não existe um cadastro de planeta com código %s", id));
    }

}
