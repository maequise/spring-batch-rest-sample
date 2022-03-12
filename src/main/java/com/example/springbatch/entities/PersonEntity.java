package com.example.springbatch.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PERSON")
@Data
public class PersonEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_generator", sequenceName = "seq_person", initialValue = 1, allocationSize = 1)
    private int idPerson;

    @Column(name="NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;
}
