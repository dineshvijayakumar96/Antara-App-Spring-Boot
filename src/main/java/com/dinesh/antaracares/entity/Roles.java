package com.dinesh.antaracares.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "role_user_name", referencedColumnName = "user_name")
    @JsonBackReference
    private Users user;

    @Column(name = "role")
    private String role;

    public Roles() {
    }

    public Roles(Users user, String role) {
        this.user = user;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", user=" + user +
                ", role='" + role + '\'' +
                '}';
    }
}
