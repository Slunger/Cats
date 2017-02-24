package com.cats.web;

import com.cats.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by andrey on 24.02.17.
 */
@RestController
@RequestMapping("/firebase")
public class FirebaseController {

    private static final Logger LOG = LoggerFactory.getLogger(FirebaseController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{token}", method = RequestMethod.PUT)
    public ResponseEntity updateToken(@PathVariable("token") String token, Principal principal) {
        LOG.info("Update token - {} for - {}", token, principal.getName());

        userService.updateTokenByUsername(principal.getName(), token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
