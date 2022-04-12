package dh.projetointegradorctd.backend.model.actor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.model.storage.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import java.util.Set;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "credentialsNonExpired", "username", "enabled"})
public class Client extends User {

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Product> favoriteProducts;
    
    @PrePersist
    private void setClientAuthorities() {
        Role role = new Role();
        role.setId((long) 2);
        role.setAuthority(Role.Authority.CLIENT);
        super.setAuthorities(Set.of(role));
    }
}
