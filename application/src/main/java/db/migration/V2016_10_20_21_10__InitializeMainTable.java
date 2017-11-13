package db.migration;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class V2016_10_20_21_10__InitializeMainTable
        implements SpringJdbcMigration {

    private static final int SIZE = 1_000_000;
//    private static final int SIZE = 100;

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        String categoriesSql = "SELECT id, category FROM domain_table";
        List<Integer> categories =
                jdbcTemplate.query(categoriesSql, (rs, rowNum) -> rs.getInt("id"));

        String mainTableInsertSql = "INSERT INTO main_table (name, value, category_id, category_id_fk) VALUES (?, ?, ?, ?)";
        List<Integer> list =
                IntStream.rangeClosed(1, SIZE)
                         .boxed()
                         .collect(Collectors.toList());

        int categoriesSize = categories.size();
        Lists.partition(list, 1000)
             .forEach(l -> {
                 List<Object[]> values = new ArrayList<>(1000);
                 l.forEach(i -> {
                     int index = getIndex(categoriesSize);
                     values.add(new Object[]{
                             RandomStringUtils.randomAlphanumeric(10),
                             RandomUtils.nextInt(1, 100),
                             index,
                             categories.get(index)
                     });
                 });

                 jdbcTemplate.batchUpdate(mainTableInsertSql, values);
             });
    }

    private int getIndex(int size) {
        return RandomUtils.nextInt(1, size);
    }
}
