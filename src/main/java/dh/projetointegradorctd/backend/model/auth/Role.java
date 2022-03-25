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

    @NotNull
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Override
    public String getAuthority() {
        return this.authority.name();
    }
}
