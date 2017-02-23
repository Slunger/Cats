package com.cats.services;

import com.cats.model.Cat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by andrey on 07.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServicesTestConfig.class})
@ActiveProfiles("dev")
public class CatServiceTest {

    private static final String COLOR = "color";
    private static final String BREED = "breed";
    private static final String NAME = "name";
    private static final int AGE = 3;
    private static final int WEIGHT = 8;

    @Autowired
    private CatService catService;

    @Test
    public void testSaveCat() {
        final Cat cat = new Cat(AGE, COLOR, BREED, NAME, WEIGHT, 0);
        catService.save(cat);
        assertNotNull(catService.get(cat.getId()));
    }

    @Test
    public void testSaveAllCats() {
        final Cat cat1 = new Cat(AGE, COLOR, BREED, NAME, WEIGHT, 0);
        final Cat cat2 = new Cat(AGE, COLOR, BREED, NAME, WEIGHT, 0);
        final Cat cat3 = new Cat(AGE, COLOR, BREED, NAME, WEIGHT, 0);
        catService.saveAll(Arrays.asList(cat1, cat2, cat3));

        Collection<Cat> catCollection = catService.saveAll(Arrays.asList(cat1, cat2, cat3));
        assertNotNull(catService.get(cat1.getId()));
        assertNotNull(catService.get(cat2.getId()));
        assertNotNull(catService.get(cat3.getId()));
        assertNotNull(catCollection);
        assertEquals(3, catCollection.size());
    }

    @Test
    public void testUpdateCat() {
        Cat cat = new Cat(AGE, COLOR, BREED, NAME, WEIGHT, 0);
        catService.save(cat);
        cat.setAge(cat.getAge() + 1);

        assertNotNull(catService.update(cat));
        assertEquals(catService.get(cat.getId()).getAge(), cat.getAge());
    }

    @Test
    public void testDeleteCat(){
        final Cat cat = new Cat(AGE, COLOR, BREED, NAME, WEIGHT, 0);
        catService.save(cat);
        catService.delete(cat.getId());
        assertNull(catService.get(cat.getId()));
    }

    @Test
    public void testGetAll(){
        assertTrue(catService.getAll().size() > 0);
    }
}
