package ru.romanow.highload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.highload.model.AverageValueInCategoryInfo;
import ru.romanow.highload.reposiroty.RequestRepository;

import java.util.List;

/**
 * Created by ronin on 21.10.16
 */
@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    // @Timed
    @Transactional(readOnly = true)
    public List<AverageValueInCategoryInfo> testDatabaseCompute() {
        return requestRepository.averageValueInCategory();
    }
}
