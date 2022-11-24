package net.backend.paylens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.backend.paylens.model.entity.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    
}
