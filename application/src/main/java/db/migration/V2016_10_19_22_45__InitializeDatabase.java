package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.IntStream;

/**
 * Created by ronin on 19.10.16
 */
public class V2016_10_19_22_45__InitializeDatabase
        implements SpringJdbcMigration {

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        String domainTableInsertSql = "INSERT INTO domain_table (category) VALUES (?)";
        IntStream.range(0, 100)
                .forEach(i -> {
                    jdbcTemplate.update(domainTableInsertSql, i);
                });
    }
}
