package ru.romanow.highload;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.romanow.highload.domain.DomainTable;
import ru.romanow.highload.domain.MainTable;
import ru.romanow.highload.model.AverageValueInCategoryInfo;
import ru.romanow.highload.reposiroty.DomainTableRepository;
import ru.romanow.highload.reposiroty.MainTableRepository;
import ru.romanow.highload.service.RequestService;

import java.util.List;

@Order(2)
@Component
@AllArgsConstructor
public class ExampleRunner
        implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final MainTableRepository mainTableRepository;
    private final DomainTableRepository domainTableRepository;
    private final RequestService requestService;

    @Override
    public void run(ApplicationArguments args) {
        logger.info("========== Database compute ==========");
        requestService.testDatabaseCompute();

        logger.info("========== In memory compute from database ==========");
        requestService.testInMemoryCompute();

        logger.info("========== In memory compute without database ==========");
        testInMemoryComputeExcludeDatabase();
    }

    private void testInMemoryComputeExcludeDatabase() {
        List<DomainTable> categories = domainTableRepository.findAll();
        List<MainTable> values = mainTableRepository.findAll();
        List<AverageValueInCategoryInfo> collect = requestService.collect(values, categories);
        collect.forEach(i -> logger.debug("{}", i));
    }
}
