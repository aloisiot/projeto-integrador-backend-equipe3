package dh.projetointegradorctd.backend.model.actor;

import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.model.storage.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Client extends User {

    @OneToMany
    private List<Product> favoriteProducts;
    
    @PrePersist
    private void setClientAuthorities() {
        Role role = new Role();
        role.setAuthority(Role.Authority.CLIENT);
        super.setAuthorities(Set.of(role));
    }
}
