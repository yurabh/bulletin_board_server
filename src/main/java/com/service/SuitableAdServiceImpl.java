package com.service;

import com.domain.SuitableAd;
import com.repository.SuitableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link SuitableAdServiceImpl} class binds realization part with user and do some actions with database such as:
 * save,find,update,delete
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Service
public class SuitableAdServiceImpl implements CRUDService<SuitableAd> {

    /**
     * Field {@link SuitableRepository} is object instance of {@link SuitableRepository} interface,
     * it helps us to perform database manipulations
     */
    private SuitableRepository suitableRepository;

    /**
     * This is a constructor {@link SuitableAdServiceImpl} with parameter that injects object gain of the
     * {@link SuitableRepository}
     *
     * @param suitableRepository {@link SuitableRepository}
     */
    @Autowired
    public SuitableAdServiceImpl(SuitableRepository suitableRepository) {
        this.suitableRepository = suitableRepository;
    }

    /**
     * This is method takes a {@link SuitableAd} object and stores it in a database
     *
     * @param entity {@link SuitableAd}
     */
    @Override
    public void save(SuitableAd entity) {
        suitableRepository.save(entity);
    }

    /**
     * This is method for searching for an suitableAd by id and pass {@link SuitableAd#id}
     * to the {@link SuitableRepository#findById(Object)} to find the suitableAd in data base
     *
     * @param id int
     * @return suitableAd {@link SuitableAd}
     */
    @Override
    public SuitableAd find(int id) {
        return suitableRepository.findById(id).get();
    }

    /**
     * This is method accepts the {@link SuitableAd} object with the new data and pass
     * it to the {@link SuitableRepository#save(Object)} to update the suitableAd
     *
     * @param suitableAd {@link SuitableAd}
     */
    @Override
    public void update(SuitableAd suitableAd) {
        suitableRepository.save(suitableAd);
    }

    /**
     * This is method takes the id of the {@link SuitableAd} object and performs the operation of deleting
     * from a database
     *
     * @param id int
     */
    @Override
    public void delete(int id) {
        suitableRepository.deleteById(id);
    }
}
