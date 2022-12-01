package net.backend.paylens.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import net.backend.paylens.model.entity.User;
import net.backend.paylens.model.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByUser(User user);
}
