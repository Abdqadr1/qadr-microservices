package com.qadr.bank.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor @Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String alias;

    private String type;

    private String code;

    private String longCode;

    @Column(name="created_time")
    private Date createdTime;

    @Column(name="updated_time")
    private Date updatedTime;

    private boolean enabled;

    private String country;

    private String currency;

    public Bank(int id, String name, String alias, String type, String code){
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.type = type;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bank bank = (Bank) o;
        return id != null && Objects.equals(id, bank.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
