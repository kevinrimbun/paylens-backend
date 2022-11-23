package net.backend.paylens.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detail_users")
@Data
@NoArgsConstructor
public class DetailUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50)
    private String phoneNumber;

    @Lob
    @Column(length = 1000)
    private byte[]  profilePicture;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    public DetailUser(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}