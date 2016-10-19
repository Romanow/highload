package ru.romanow.highload.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;

/**
 * Created by ronin on 19.10.16
 */
@Entity
@Table(name = "domain_table")
public class DomainTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String category;

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public DomainTable setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainTable)) {
            return false;
        }
        DomainTable that = (DomainTable) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, category);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("category", category)
                          .toString();
    }
}
