package ru.romanow.highload;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.highload.domain.DomainTable;
import ru.romanow.highload.domain.MainTable;
import ru.romanow.highload.reposiroty.DomainTableRepository;
import ru.romanow.highload.reposiroty.MainTableRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;

@Order(1)
@Component
@DependsOn("entityManagerFactory")
@AllArgsConstructor
public class DatabaseInitializer
        implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    private static final int CATEGORIES_SIZE = 1000;
    private static final int MAIN_TABLE_SIZE = 1_000_000;

    private final DomainTableRepository domainTableRepository;
    private final MainTableRepository mainTableRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        logger.info("========== Start database initializer ==========");

        List<DomainTable> categories = rangeClosed(0, CATEGORIES_SIZE)
                .boxed()
                .map(i -> new DomainTable().setCategory("Category" + i))
                .collect(toList());

        List<DomainTable> updated = domainTableRepository.saveAll(categories);

        int categoriesSize = categories.size();
        List<Integer> list = rangeClosed(1, MAIN_TABLE_SIZE).boxed().collect(Collectors.toList());

        Lists.partition(list, 1000)
                .forEach(l -> {
                    List<MainTable> values = new ArrayList<>(1000);
                    l.forEach(i -> {
                        int index = getIndex(categoriesSize);
                        final MainTable mainTable =
                                new MainTable()
                                        .setName(randomAlphanumeric(10))
                                        .setValue(nextInt(1, 100))
                                        .setCategory(updated.get(index));
                        values.add(mainTable);
                    });

                    mainTableRepository.saveAll(values);
                });

        logger.info("========== Finish database initializer ==========");
    }

    private int getIndex(int size) {
        return nextInt(1, size);
    }
}
