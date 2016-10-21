package ru.romanow.highload.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.romanow.highload.domain.MainTable;
import ru.romanow.highload.model.AverageValueInCategoryInfo;

import java.util.List;

public interface MainTableRepository
        extends JpaRepository<MainTable, Integer> {

    @Query("select new ru.romanow.highload.model.AverageValueInCategoryInfo(avg(m.value), d.category) " +
            "from MainTable m " +
            "join m.category d " +
            "group by d.category " +
            "order by d.category")
    List<AverageValueInCategoryInfo> averageValueInCategory();
}