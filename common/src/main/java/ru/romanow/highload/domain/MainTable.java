package ru.romanow.highload.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * Created by ronin on 19.10.16
 */
@Entity
@Table(name = "main_table")
public class MainTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private DomainTable category;

    public Integer getId() {
        return id;
    }

    public MainTable setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MainTable setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public MainTable setValue(Integer value) {
        this.value = value;
        return this;
    }

    public DomainTable getCategory() {
        return category;
    }

    public MainTable setCategory(DomainTable category) {
        this.category = category;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MainTable)) {
            return false;
        }
        MainTable mainTable = (MainTable) o;
        return Objects.equal(id, mainTable.id) &&
                Objects.equal(name, mainTable.name) &&
                Objects.equal(value, mainTable.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("name", name)
                          .add("value", value)
                          .toString();
    }
}
