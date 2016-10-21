package ru.romanow.highload.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanow.highload.domain.DomainTable;

/**
 * Created by romanow on 21.10.16
 */
public interface DomainTableRepository
        extends JpaRepository<DomainTable, Integer> {}
