package dh.projetointegradorctd.backend.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dh.projetointegradorctd.backend.model.actor.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "tb_evaluations")
public class Evaluation extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A avaliação deve conter um comentário")
    @Column(nullable = false, length = 500)
    private String comment;

    @NotNull(message = "A avaliação deve conter uma quantidade de estrelas")
    @Max(value = 5, message = "O maximo de estrelas é 5")
    @Min(value = 0, message = "O minimo de estrelas é 0")
    @Column(nullable = false)
    private Integer stars;

    @NotNull(message = "A avaliacao deve fazer referência a um produto")
    @ManyToOne
    @JsonIgnoreProperties({"images", "characteristics", "city", "category", "evaluations"})
    private Product product;

    @NotNull(message = "A avaliacao deve fazer referência a um cliente")
    @ManyToOne
    @JsonIgnoreProperties({"favoriteProducts", "authorities", "email"})
    private Client client;
}
