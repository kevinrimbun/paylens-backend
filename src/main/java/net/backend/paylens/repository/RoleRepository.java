package net.backend.paylens.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import net.backend.paylens.model.entity.ERole;
import net.backend.paylens.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(ERole roleName);
}
