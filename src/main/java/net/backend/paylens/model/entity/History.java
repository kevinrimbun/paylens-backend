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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Relation to transfer table
    @ManyToOne
    @JoinColumn(name = "transfer_id")
    private Transfer transfer;

    // Relation to top up table
    @ManyToOne
    @JoinColumn(name = "top_up_id")
    private TopUp topUp;

    private Boolean isDeleted = false;

}
