package dh.projetointegradorctd.backend.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import dh.projetointegradorctd.backend.model.storage.DataBaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tb_users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email"}) })
public class User extends DataBaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo")
    @Column(length = 100)
    @Size(min = 2, max = 100, message = "Comprimento invalido para o campo nome")
    private String name;

    @NotBlank(message = "O ultimo nome não deve estár em branco")
    @Column(length = 100)
    @Size(min = 2, max = 100, message = "Comprimento invalido para o campo sobrenome")
    private String lastname;

    @Email(message = "email invalido")
    private String email;

    @NotBlank(message = "A senha não deve estár em branco")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 255, message = "A senha deve conter entre 8 e 255 caracters")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull(message = "Autorizações não devem ser nulas para o usuário")
    private Set<Role> authorities;

    @Column(nullable = false)
    private Boolean isEmailChecked;

    @PrePersist
    private void prePersist() {
        setIsEmailChecked(false);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override // TODO
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // TODO
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // TODO
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // TODO
    public boolean isEnabled() {
        return true;
    }
}
