package dh.projetointegradorctd.backend.model.actor;

import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.model.auth.User;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.util.Set;

@Entity
public class Client extends User {
    
    @PrePersist
    private void setClientAuthorities() {
        Role role = new Role();
        role.setAuthority(Role.Authority.CLIENT);
        super.setAuthorities(Set.of(role));
    }
}
