package com.plantations.management.system.PMS.Model;
import jakarta.persistence.*;
@Entity
@Table(name = "Requests")
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Request_Id")
    private Integer requestId;
    @ManyToOne
    @JoinColumn(name="Owner_Id")
    private Owners ownerId;
    @Column(name="Category")
    private String category;
    @Column(name="Description")
    private String Description;
    @Column(name="Status")
    private String status;

    public Requests() {
    }

    public Requests(Integer requestId, Owners ownerId, String category, String description, String status) {
        this.requestId = requestId;
        this.ownerId = ownerId;
        this.category = category;
        Description = description;
        this.status = status;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Owners getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Owners ownerId) {
        this.ownerId = ownerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
