package org.binar.eflightticket_b2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Notification extends BaseEntity{

    private String message;

    @Column(name = "is_read")
    private Boolean isRead;

    @ManyToOne
    private Users users;


}
