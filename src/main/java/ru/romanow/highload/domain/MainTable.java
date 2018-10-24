package ru.romanow.highload.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
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

    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_main_table_category"))
    private DomainTable category;

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
