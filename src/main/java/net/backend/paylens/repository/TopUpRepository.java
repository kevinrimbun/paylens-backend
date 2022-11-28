package net.backend.paylens.repository;

import net.backend.paylens.model.entity.TopUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopUpRepository extends JpaRepository<TopUp, Long> {
}
