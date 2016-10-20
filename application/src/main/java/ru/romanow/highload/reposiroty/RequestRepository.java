package ru.romanow.highload.reposiroty;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.romanow.highload.domain.MainTable;

import java.util.List;

/**
 * Created by ronin on 20.10.16
 */
public interface RequestRepository
        extends CrudRepository<MainTable, Integer> {

    @Query("select new ru.romanow.highload.model.AverageValueInCategoryInfo(avg(m.value), d.category) " +
            "from MainTable m " +
            "join m.category d " +
            "group by d.category " +
            "order by d.category")
    List<MainTable> averageValueInCategory();
}