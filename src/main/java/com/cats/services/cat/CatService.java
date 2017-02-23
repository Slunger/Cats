package com.cats.services.cat;

import com.cats.model.Cat;

import java.util.Collection;

/**
 * Created by andrey on 07.02.17.
 */
public interface CatService {

    Cat save(Cat cat);

    Cat update(Cat cat);

    Collection<Cat> saveAll(Collection<Cat> cats);

    void delete(Integer catId);

    Cat get(Integer catId);

    Collection<Cat> getAll();

    void like(Integer catId);
}
