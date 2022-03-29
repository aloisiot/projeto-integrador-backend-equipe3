package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.global.EmpityRepositoryException;
import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> findAll() {
        List<Role> roles = repository.findAll();
        if (roles.isEmpty()) {
            throw new EmpityRepositoryException("Repositório de perfis vazio");
        }
        return roles;
    }
}
