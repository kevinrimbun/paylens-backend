package net.backend.paylens.repository;

import net.backend.paylens.model.entity.DetailUser;
import net.backend.paylens.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailUserRepository extends JpaRepository<DetailUser, Long> {

    Optional<DetailUser> findByUser(User user);
}
