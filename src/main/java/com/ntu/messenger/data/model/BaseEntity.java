package com.ntu.messenger.data.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    protected Long id;

    @CreationTimestamp
    protected Date createdDate;

    @UpdateTimestamp
    protected Date updatedDate;

}
