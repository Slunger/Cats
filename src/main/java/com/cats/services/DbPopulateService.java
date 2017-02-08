package com.cats.services;

import com.cats.model.Cat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by andrey on 08.02.17.
 */
@Component
public class DbPopulateService {

    private static final Logger LOG = LoggerFactory.getLogger(DbPopulateService.class);

    private static final String CAT_IS_SAVED = "cat {} is saved";

    @Autowired
    private CatService catService;

    @PostConstruct
    private void populate() {
        if (!catService.getAll().isEmpty()) {
            return;
        }

        LOG.info("going to populate database with test data");

        int age = 1, weight = 1;
        Cat cat = new Cat(age++, "white", "Cyprus", "Kitty", weight++);
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age++, "lavender", "Sphynx", "Byte", weight++);
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age, "chocolate", "European Shorthair", "Yoda", weight);
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age++, "tortoiseshell", "Siamese", "Picasso", weight++);
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age++, "black", "Exotic Shorthair", "Lancelot", weight++);
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);

        cat = new Cat(age, "chocolate", "Asian", "eEwok", weight);
        catService.save(cat);
        LOG.info(CAT_IS_SAVED, cat);
    }
}
