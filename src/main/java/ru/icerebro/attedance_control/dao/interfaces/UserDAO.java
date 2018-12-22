package ru.icerebro.attedance_control.dao.interfaces;

import ru.icerebro.attedance_control.entities.User;

public interface UserDAO {
    User getUser(String username);
    int saveUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
}
