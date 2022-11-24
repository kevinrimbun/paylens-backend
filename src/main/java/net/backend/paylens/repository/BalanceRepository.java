package net.backend.paylens.repository;

import net.backend.paylens.model.entity.Balance;
import net.backend.paylens.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByDate(LocalDateTime localDateTime);
}
