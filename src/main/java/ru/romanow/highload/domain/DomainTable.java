package ru.romanow.highload.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "domain_table")
public class DomainTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String category;

    @OneToMany(mappedBy = "category")
    private List<MainTable> values;

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
