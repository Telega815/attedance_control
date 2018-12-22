package ru.icerebro.attedance_control.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.icerebro.attedance_control.dao.interfaces.GroupDAO;
import ru.icerebro.attedance_control.dao.interfaces.UserDAO;
import ru.icerebro.attedance_control.entities.Group;
import ru.icerebro.attedance_control.entities.User;
import ru.icerebro.attedance_control.services.interfaces.UserService;


public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserDAO userDAO;

    private final GroupDAO groupDAO;

    @Autowired
    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, UserDAO userDAO, GroupDAO groupDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userDAO.getUser(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(username+" not found");
        }

        return new org.springframework.security.core.userdetails
                .User(username, user.getPwd(), true, true, true, true, user.getGroup().getAuthorities());
    }

    public void createUser(User user) {
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        Group group = groupDAO.getGroup("USERS");
        if (group == null){
            throw new NullPointerException("Couldn't find group USERS");
        }else {
            user.setGroup(group);
            userDAO.saveUser(user);
        }
    }

}
