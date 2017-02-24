package com.cats.services.user;

import com.cats.dao.Dao;
import com.cats.model.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by andrey on 23.02.17.
 */
@Service("userService")
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    private Dao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User get(Integer userId) {
        return dao.get(User.class, userId);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class)
                .add(Property.forName("username").eq(s));

        return dao.getBy(detachedCriteria);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return dao.save(user);
    }

    @Override
    public List<User> findAll() {
        return dao.all(User.class);
    }

    @Override
    public void updateTokenByUsername(String username, String token) {

        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class)
                .add(Property.forName("username").eq(username));

        User user = dao.getBy(detachedCriteria);
        user.setToken(token);
        dao.update(user);
    }
}
