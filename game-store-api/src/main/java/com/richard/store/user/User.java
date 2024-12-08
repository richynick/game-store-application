package com.richard.store.user;

import com.richard.store.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class User extends BaseEntity {

    private String lastName;
    private String firstName;
    private String email;
    private String profilePictureUrl;

}
