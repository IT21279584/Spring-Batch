package com.example.MultiStepsTasklet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Newstudent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentid")
    private int studentID;

    @Column(name = "nic")
    private String nic;

    @Column(name = "firstname")
    private String firstname;

}
