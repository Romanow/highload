package ru.romanow.highload.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.highload.annotation.Timed;
import ru.romanow.highload.domain.DomainTable;
import ru.romanow.highload.domain.MainTable;
import ru.romanow.highload.model.AverageValueInCategoryInfo;
import ru.romanow.highload.reposiroty.DomainTableRepository;
import ru.romanow.highload.reposiroty.MainTableRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.by;

@Service
@AllArgsConstructor
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    private final MainTableRepository mainTableRepository;
    private final DomainTableRepository domainTableRepository;

    @Timed("testDatabaseCompute")
    @Transactional(readOnly = true)
    public void testDatabaseCompute() {
        List<AverageValueInCategoryInfo> list = mainTableRepository.averageValueInCategory();
        list.forEach(i -> logger.debug("{}", i));
    }

    @Timed("testInMemoryCompute")
    @Transactional(readOnly = true)
    public void testInMemoryCompute() {
        List<DomainTable> categories = domainTableRepository.findAll(by(new Order(ASC, "id")));
        List<MainTable> values = mainTableRepository.findAll();

        List<AverageValueInCategoryInfo> list = collect(values, categories);
        list.forEach(i -> logger.debug("{}", i));
    }

    @Timed("testInMemoryComputeExcludeDatabase")
    public List<AverageValueInCategoryInfo> collect(List<MainTable> values, List<DomainTable> categories) {
        return values.stream()
                .collect(Collectors.groupingBy(MainTable::getCategoryId, Collectors.averagingInt(MainTable::getValue)))
                .entrySet().stream()
                .map(entry -> new AverageValueInCategoryInfo(entry.getValue(), findCategory(categories, entry.getKey())))
                .collect(Collectors.toList());
    }

    private String findCategory(List<DomainTable> categories, Integer key) {
        return categories.stream()
                .filter(c -> c.getId().equals(key))
                .findFirst()
                .map(DomainTable::getCategory)
                .orElse(null);
    }
}
