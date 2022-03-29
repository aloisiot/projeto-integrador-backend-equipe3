package dh.projetointegradorctd.backend.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "tb_roles", uniqueConstraints = { @UniqueConstraint(columnNames = { "authority"}) })
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
