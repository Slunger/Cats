package com.cats.web;

import com.cats.model.Cat;
import com.cats.services.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Created by andrey on 07.02.17.
 */
@RestController
@RequestMapping("/cats")
public class CatRestController {

    private static final Logger LOG = LoggerFactory.getLogger(CatRestController.class);

    @Autowired
    private CatService catService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        Collection<Cat> catList = catService.getAll();
        return new ResponseEntity<>(catList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createCat(@Valid Cat cat, BindingResult bindingResult) {
        LOG.info(cat.toString());

        if (bindingResult.hasErrors()) {
            LOG.info(bindingResult.getAllErrors().toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        catService.save(cat);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)//POST
    public ResponseEntity update(@Valid Cat cat, BindingResult bindingResult) {
        LOG.info(cat.toString());

        if (bindingResult.hasErrors()) {
            LOG.info(bindingResult.getAllErrors().toString());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        catService.update(cat);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCat(@PathVariable("id") Integer id) {
        catService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getCat(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(catService.get(id), HttpStatus.OK);
    }
}
