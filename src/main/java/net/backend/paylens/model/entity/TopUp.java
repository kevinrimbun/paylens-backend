package net.backend.paylens.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "top_up")
@Data
@NoArgsConstructor
public class TopUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
