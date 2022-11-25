package net.backend.paylens.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "top_up")
@Entity
@Data
@NoArgsConstructor
public class TopUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //one to one
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private Long topAmount;

    @Column
    private String notes;
}
