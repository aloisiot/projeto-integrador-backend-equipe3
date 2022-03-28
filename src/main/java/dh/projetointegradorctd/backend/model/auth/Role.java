package dh.projetointegradorctd.backend.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Embeddable
public class Role implements GrantedAuthority {

    @NotNull(message = "A autorização do perfil deve ser especificada")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Role.Authority authority;

    public String getAuthority() {
        return this.authority.name();
    }

    public enum Authority {
        ADMIN, CLIENT
    }
}