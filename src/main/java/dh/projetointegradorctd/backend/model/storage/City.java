package dh.projetointegradorctd.backend.model.storage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "tb_cities")
public class City extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 255, message = "comprimento invalido para o campo nome")
    private String name;

    @NotBlank
    @Column(length = 3)
    @Size(min = 2, max = 2, message = "campo pais deve conter apenas dois (2) caracters")
    private String country;
}
