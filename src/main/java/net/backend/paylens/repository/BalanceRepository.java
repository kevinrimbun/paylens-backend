package net.backend.paylens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.backend.paylens.model.entity.Balance;
import net.backend.paylens.model.entity.User;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByUserId(User id);
}
