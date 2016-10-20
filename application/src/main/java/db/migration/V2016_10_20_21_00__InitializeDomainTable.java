package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by ronin on 19.10.16
 */
public class V2016_10_20_21_00__InitializeDomainTable
        implements SpringJdbcMigration {

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        String domainTableInsertSql = "INSERT INTO domain_table (category) VALUES (?)";
        List<Object[]> categories =
                IntStream.rangeClosed(0, 100)
                         .boxed()
                         .map(i -> new Object[]{ "Category " + i })
                         .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(domainTableInsertSql, categories);
    }
}
