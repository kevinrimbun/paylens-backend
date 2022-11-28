package net.backend.paylens.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "history")
@Data
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Relation to users table
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Relation to transfer table
    @OneToOne
    @JoinColumn(name = "transfer_id")
    private Transfer transfer;

    // Relation to top up table
    @OneToOne
    @JoinColumn(name = "top_up_id")
    private TopUp topUp;

    private Boolean isDeleted = false;
}
