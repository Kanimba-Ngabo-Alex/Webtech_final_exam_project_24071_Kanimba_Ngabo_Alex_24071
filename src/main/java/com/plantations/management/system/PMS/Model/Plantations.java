package com.plantations.management.system.PMS.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Plantations")
public class Plantations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Plantation_Id")
    private Integer plantationId;
    @ManyToOne
    @JoinColumn(name="Owner_Id")
    private Owners ownerId;
    @ManyToOne
    @JoinColumn(name="Crop_Id")
    private Crops cropId;
    @Column(name="Location")
    private String location;

    public Plantations() {
    }

    public Plantations(Integer plantationId, Owners ownerId, Crops cropId, String location) {
        this.plantationId = plantationId;
        this.ownerId = ownerId;
        this.cropId = cropId;
        this.location = location;
    }

    public Integer getPlantationId() {
        return plantationId;
    }

    public void setPlantationId(Integer plantationId) {
        this.plantationId = plantationId;
    }

    public Owners getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Owners ownerId) {
        this.ownerId = ownerId;
    }

    public Crops getCropId() {
        return cropId;
    }

    public void setCropId(Crops cropId) {
        this.cropId = cropId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
