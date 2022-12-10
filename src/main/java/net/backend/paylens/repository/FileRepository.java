package net.backend.paylens.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.backend.paylens.model.entity.FileUpload;
import net.backend.paylens.model.entity.User;

@Repository
public interface FileRepository extends JpaRepository<FileUpload, String> {
    Optional<FileUpload> findByUser(User user);
    
}
