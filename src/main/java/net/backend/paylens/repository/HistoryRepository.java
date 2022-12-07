package net.backend.paylens.repository;

import net.backend.paylens.model.entity.History;
import net.backend.paylens.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByIsDeleted(Boolean status);
    Optional<List<History>> findByUser(User user);



}
