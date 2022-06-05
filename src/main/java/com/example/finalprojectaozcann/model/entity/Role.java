package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseExtendedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Role extends BaseExtendedEntity {

    private String name;

    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name) && Objects.equals(user, role.user);
    }
}
