package dh.projetointegradorctd.backend.model.storage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tb_products")
public class Product extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 255, message = "comprimento invalido para o campo descricao")
    private String description;

    @NotBlank
    @Size(min = 5, max = 255,message = "comprimento invalido para o campo nome")
    private String name;

    @ManyToOne
    @NotNull
    private Category category;

    @ManyToOne
    private City city;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @ManyToMany
    private List<Characteristic> characteristics;

    private String latitude;
    private String longitude;
}
