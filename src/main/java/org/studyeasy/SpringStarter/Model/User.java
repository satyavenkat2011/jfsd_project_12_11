package org.studyeasy.SpringStarter.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String role; // Role of the user: "USER" or "ADMIN"

    // House-related fields
    @Column
    private String kitchenSize;

    @Column
    private String woodPreference;

    @Column
    private String flooringType;

    @Column
    private Integer numberOfBedrooms;

    @Column
    private String wallColor;

    @Column
    private Boolean openFloorPlan;

    @Column
    private Double remodelingBudget;

    // Constructors
    public User() {
    }

    public User(String email, String password, String username, String fullName, String role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getKitchenSize() {
        return kitchenSize;
    }

    public void setKitchenSize(String kitchenSize) {
        this.kitchenSize = kitchenSize;
    }

    public String getWoodPreference() {
        return woodPreference;
    }

    public void setWoodPreference(String woodPreference) {
        this.woodPreference = woodPreference;
    }

    public String getFlooringType() {
        return flooringType;
    }

    public void setFlooringType(String flooringType) {
        this.flooringType = flooringType;
    }

    public Integer getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public String getWallColor() {
        return wallColor;
    }

    public void setWallColor(String wallColor) {
        this.wallColor = wallColor;
    }

    public Boolean getOpenFloorPlan() {
        return openFloorPlan;
    }

    public void setOpenFloorPlan(Boolean openFloorPlan) {
        this.openFloorPlan = openFloorPlan;
    }

    public Double getRemodelingBudget() {
        return remodelingBudget;
    }

    public void setRemodelingBudget(Double remodelingBudget) {
        this.remodelingBudget = remodelingBudget;
    }
}
