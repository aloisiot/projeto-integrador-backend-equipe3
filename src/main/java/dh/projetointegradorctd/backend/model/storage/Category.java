package dh.projetointegradorctd.backend.model.storage;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@Setter
@Table(name = "tb_categories")
public class Category extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A categoria deve ter uma qualificação")
    private String qualification;

    @NotBlank(message = "A categoria deve ter uma descrição")
    private String description;

    @NotBlank(message = "A categoria deve ter a url de uma imagem associada")
    @URL(message = "A url da imagem não é válida")
    private String urlImage;

    @Transient
    @PositiveOrZero(message = "A quantidade de produtos da categoria deve ser positiva ou igual a zero")
    private Integer productsQuantity;
}
