package org.binar.eflightticket_b2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity{

    private String message;

    @Column(name = "is_read")
    private Boolean isRead;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Users users;


}
