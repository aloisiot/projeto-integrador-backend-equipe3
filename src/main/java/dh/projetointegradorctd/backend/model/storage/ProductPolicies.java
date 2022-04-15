package dh.projetointegradorctd.backend.model.storage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@Setter
public class ProductPolicies {

    @NotBlank(message = "As politicas gerais devem ser especificadas")
    @Column(length = 500, nullable = false)
    private String generalRules;

    @NotBlank(message = "As politicas de saude e seguranca devem ser especificadas")
    @Column(length = 500, nullable = false)
    private String cheersAndSecurity;

    @NotBlank(message = "As politicas de cancellation devem ser especificadas")
    @Column(length = 500, nullable = false)
    private String cancellation;
}
