package com.cats.services;

import com.cats.dao.Dao;
import com.cats.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by andrey on 07.02.17.
 */
@Service("catService")
@Transactional
public class CatServiceImpl implements CatService {

    @Autowired
    private Dao dao;

    @Override
    public Cat save(Cat cat) {
        return dao.save(cat);
    }

    @Override
    public Cat update(Cat cat) {
        return dao.update(cat);
    }

    @Override
    public Collection<Cat> saveAll(Collection<Cat> cats) {
        return dao.saveAll(cats);
    }

    @Override
    public void delete(Integer catId) {
        final Cat cat = dao.get(Cat.class, catId);
        dao.delete(cat);
    }

    @Override
    public Cat get(Integer catId) {
        return dao.get(Cat.class, catId);
    }

    @Override
    public Collection<Cat> getAll() {
        return dao.all(Cat.class);
    }

    @Override
    public void like(Integer catId) {
        Cat cat = dao.get(Cat.class, catId);
        cat.setLikes(cat.getLikes() + 1);
        dao.save(cat);
    }
}
