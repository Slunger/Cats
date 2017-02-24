package com.cats.web;

import com.cats.model.Cat;
import com.cats.services.AndroidPushNotificationsService;
import com.cats.services.cat.CatService;
import com.cats.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @Autowired
    private UserService userService;

    @Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll() {
        Collection<Cat> catList = catService.getAll();
        if (catList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(catList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createCat(@RequestBody Cat cat) {
        LOG.info(cat.toString());

        catService.save(cat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody Cat cat) {
        LOG.info(cat.toString());

        if (catService.get(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cat.setId(id);
        catService.update(cat);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCat(@PathVariable("id") Integer id) {
        if (catService.get(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getCat(@PathVariable("id") Integer id) {
        Cat cat = catService.get(id);
        if (cat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    @RequestMapping(value = "/liked/{id}", method = RequestMethod.PUT)
    public ResponseEntity catLiked(@PathVariable("id") final Integer catId, Principal principal) {
        androidPushNotificationsService
                .sendNotification(principal.getName() + " liked your cat id=" + catId,
                        userService.get(catService.like(catId).getUserId()).getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
