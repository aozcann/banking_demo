package com.example.finalprojectaozcann.model.entity;

import com.example.finalprojectaozcann.model.base.BaseExtendedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Role extends BaseExtendedEntity {

    private String name;

    @ManyToOne
    private User user;
}
