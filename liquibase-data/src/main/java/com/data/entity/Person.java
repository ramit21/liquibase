package com.data.entity;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.persistence.*;

@Entity
@Table(name = "PERSON")
@Value
@ToString
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(generator = "person-sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "person-sequence", sequenceName = "PERSON_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;
}
