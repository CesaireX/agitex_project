package com.agitex.project.agitex.model;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
@Entity
@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.FIELD)
public class client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    @XmlElement
    private String nom;
    @Column(name = "prenom")
    @XmlElement
    private String prenom;
    @Column(name = "age")
    @XmlElement
    private Integer age;
    @Column(name = "profession")
    @XmlElement
    private String profession;
    @Column(name = "revenu")
    @XmlElement
    private Double revenu;
}
