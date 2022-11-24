package net.backend.paylens.repository;

import net.backend.paylens.model.entity.History;
import net.backend.paylens.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    // For history service purpose
    Optional<History> findById(User id);
}
