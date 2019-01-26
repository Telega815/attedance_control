package ru.icerebro.attedance_control.services.interfaces;

import ru.icerebro.attedance_control.entities.User;

public interface UserService {
    void createUser(User user);
    void createAdmin(User user);

}
