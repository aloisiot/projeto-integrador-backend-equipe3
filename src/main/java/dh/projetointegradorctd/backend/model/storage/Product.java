package dh.projetointegradorctd.backend.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(nullable = false)
    private String description;

    @NotBlank
    @Size(min = 5, max = 255,message = "comprimento invalido para o campo nome")
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Deve haver uma categoria para o produto")
    private Category category;

    @ManyToOne
    @NotNull(message = "Deve haver uma cidade para o produto")
    private City city;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private List<Image> images;

    @ManyToMany
    private List<Characteristic> characteristics;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "product")
    @JsonIgnore
    private List<Evaluation> evaluations;

    private String latitude;
    private String longitude;
}
