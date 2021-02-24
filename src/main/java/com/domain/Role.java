package com.domain;

import com.domain.enums.ROLE;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class {@link Role} with parameters: id,role.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "roles")
public class Role {

    /**
     * Field role id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;


    /**
     * Field roleAccount of the ROLE.
     */
    @Column(name = "role", nullable = false, unique = true)
    @Enumerated(value = EnumType.ORDINAL)
    private ROLE roleAccount;
}
