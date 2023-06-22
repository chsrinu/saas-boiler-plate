package com.hrms.hrpitch.common.dao;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role {
    public Role(String name) {
        this.name = name;
    }
    @Id
    @Column(name = "role_id")
    private Integer id;

    @Column(nullable = false, length = 45, unique = true, name = "role_name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
