package com.plantations.management.system.PMS.Model;
import jakarta.persistence.*;
@Entity
@Table(name = "Crops")
public class Crops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Crop_Id")
    private Integer cropId;

    @Column(name="Crop_Name")
    private String cropName;

    public Crops() {
    }

    public Crops(Integer cropId, String cropName) {
        this.cropId = cropId;
        this.cropName = cropName;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
    @Override
    public String toString() {
        return cropName;
    }
}
