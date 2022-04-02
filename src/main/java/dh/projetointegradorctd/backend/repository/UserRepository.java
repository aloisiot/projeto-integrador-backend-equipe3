package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório de usuários
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Verifica se existe um usuário com o email especificado.
     * @param email Email a ser verificado.
     * @return true caso exista, false caso não.
     */
    @Query("select (count(u) > 0) from User u where u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    /**
     * Busca um usuário com base no email
     * @param email Email referência para a busca
     * @return Optional contendo uma instância do usuário buscado.
     * Optional vazio caso não exista um usuário com o email expecificado.
     */
    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}