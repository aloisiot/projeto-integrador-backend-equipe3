package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}