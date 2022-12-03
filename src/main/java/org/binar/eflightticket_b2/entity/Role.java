package org.binar.eflightticket_b2.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Role extends BaseEntity{

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Users> users = new LinkedList<>();

}
