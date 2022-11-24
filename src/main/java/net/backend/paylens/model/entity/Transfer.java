package net.backend.paylens.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Transfer")
@Entity
@Data
@NoArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private Long amount;

    @Column(length = 250)
    private String notes;

    @DateTimeFormat(iso = ISO.DATE, fallbackPatterns = { "M/d/yy", "dd.MM.yyyy" })
    private String tanggal;

    // @ManyToOne
    // @JoinColumn(name = "User_id")
    // private User user;
}