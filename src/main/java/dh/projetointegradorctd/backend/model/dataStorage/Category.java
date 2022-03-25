package dh.projetointegradorctd.backend.model.dataStorage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tb_categorias")
public class Category extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String qualification;
    private String description;
    private String urlImage;

    @Transient
    private Integer productsQuantity;
}
