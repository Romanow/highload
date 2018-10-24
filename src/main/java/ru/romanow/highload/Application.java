package ru.romanow.highload;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import ru.romanow.highload.domain.DomainTable;
import ru.romanow.highload.domain.MainTable;
import ru.romanow.highload.model.AverageValueInCategoryInfo;
import ru.romanow.highload.reposiroty.DomainTableRepository;
import ru.romanow.highload.reposiroty.MainTableRepository;
import ru.romanow.highload.service.RequestService;

import java.util.List;

@AllArgsConstructor
@SpringBootApplication
public class Application
        implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final MainTableRepository mainTableRepository;
    private final DomainTableRepository domainTableRepository;
    private final RequestService requestService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        requestService.testDatabaseCompute();

        requestService.testInMemoryCompute();

        testInMemoryComputeExcludeDatabase();
    }

    private void testInMemoryComputeExcludeDatabase() {
        List<DomainTable> categories = domainTableRepository.findAll();
        List<MainTable> values = mainTableRepository.findAll();
        List<AverageValueInCategoryInfo> collect = requestService.collect(values, categories);
        collect.forEach(i -> logger.debug("{}", i));
    }
}
