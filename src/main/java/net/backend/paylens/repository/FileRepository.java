package net.backend.paylens.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.backend.paylens.model.entity.FileUpload;

@Repository
public interface FileRepository extends JpaRepository<FileUpload, String> {
    
}
