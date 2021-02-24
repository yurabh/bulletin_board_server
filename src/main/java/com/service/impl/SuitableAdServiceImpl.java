package com.service.impl;

import com.domain.SuitableAd;
import com.dto.SuitableAdDto;
import com.repository.SuitableRepository;
import com.service.CRUDService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link SuitableAdServiceImpl} class binds realization part with user and
 * do some actions with database such as: save,find,update,delete.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Service
public class SuitableAdServiceImpl implements CRUDService<SuitableAdDto> {


    /**
     * Field {@link SuitableRepository} is object instance of
     * {@link SuitableRepository} interface,
     * it helps us to perform database manipulations.
     */
    private final SuitableRepository suitableRepository;


    /**
     * This is {@link org.modelmapper.ModelMapper} for converting objects.
     */
    private final ModelMapper modelMapper;


    /**
     * This is a constructor {@link SuitableAdServiceImpl} with parameter
     * that injects objects gain of the {@link SuitableRepository}
     * and {@link ModelMapper}.
     *
     * @param repositorySuitable {@link SuitableRepository}.
     * @param mapperModel        {@link ModelMapper}.
     */
    @Autowired
    public SuitableAdServiceImpl(final SuitableRepository repositorySuitable,
                                 final ModelMapper mapperModel) {
        this.suitableRepository = repositorySuitable;
        this.modelMapper = mapperModel;
    }


    /**
     * This is method takes a {@link SuitableAdDto} object and convert it to the
     * {@link com.domain.SuitableAd} and save it in the database.
     *
     * @param suitableAdDto {@link SuitableAdDto}.
     */
    @Override
    public void save(final SuitableAdDto suitableAdDto) {
        final SuitableAd suitableAd = modelMapper.
                map(suitableAdDto, SuitableAd.class);
        suitableRepository.save(suitableAd);
    }


    /**
     * This is method for searching for an suitableAd by id and pass
     * {@link SuitableAd#id} to the {@link SuitableRepository#findById(Object)}
     * to find the suitableAd in database and return converted
     * {@link SuitableAdDto} to the controller layer.
     *
     * @param id int.
     * @return suitableAdDto {@link SuitableAdDto}.
     */
    @Override
    public SuitableAdDto find(final int id) {
        final SuitableAd suitableAd = suitableRepository
                .findById(id).orElse(null);
        if (suitableAd == null) {
            return null;
        }
        return modelMapper.map(suitableAd, SuitableAdDto.class);
    }


    /**
     * This is method accepts the {@link SuitableAdDto} object with the new
     * data and pass it to the {@link SuitableRepository#save(Object)}
     * to update the suitableAd.
     *
     * @param suitableAdDto {@link SuitableAdDto}.
     */
    @Override
    public void update(final SuitableAdDto suitableAdDto) {
        final SuitableAd suitableAdUpdate = modelMapper.
                map(suitableAdDto, SuitableAd.class);
        suitableRepository.save(suitableAdUpdate);
    }


    /**
     * This is method takes the id of the {@link SuitableAdDto} object
     * performs the operation of deleting from a database.
     *
     * @param id int.
     */
    @Override
    public void delete(final int id) {
        suitableRepository.deleteById(id);
    }
}
