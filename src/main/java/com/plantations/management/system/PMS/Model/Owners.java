package com.plantations.management.system.PMS.Model;
import jakarta.persistence.*;
import org.apache.tomcat.util.codec.binary.Base64;

@Entity
@Table(name = "Owners")
public class Owners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Owner_Id")
    private Integer ownerId;
    @ManyToOne
    @JoinColumn(name="User_Id")
    private Users userId;
    @Column(name="Full_Names")
    private String fullNames;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "Profile_Picture", columnDefinition = "LONGBLOB")
    private byte[] profilePicture;


    public Owners() {
    }

    public Owners(Integer ownerId, Users userId, String fullNames, byte[] profilePicture) {
        this.ownerId = ownerId;
        this.userId = userId;
        this.fullNames = fullNames;
        this.profilePicture = profilePicture;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getFullNames() {
        return fullNames;
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
    @Override
    public String toString() {
        return fullNames;
    }
}
