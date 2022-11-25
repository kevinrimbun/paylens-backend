package net.backend.paylens.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
// import javax.persistence.Lob;
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
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 20)
    private String phoneNumber;
    @Column(nullable = false)
    private String pin;
    // @Lob
    // @Column(length = 1000)
    // private byte[]  profilePicture;

    // Relation to users table
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor
    public DetailUser(String firstName, String lastName, String phoneNumber, String pin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        // this.profilePicture = profilePicture;
    }
}