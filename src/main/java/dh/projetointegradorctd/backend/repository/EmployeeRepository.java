package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.actor.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}