package br.com.apiplanetas.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("TB_PLANETA")
public class Planeta {

    @Id
    private String id;
    private String nome;
    private String clima;
    private String terreno;

}
