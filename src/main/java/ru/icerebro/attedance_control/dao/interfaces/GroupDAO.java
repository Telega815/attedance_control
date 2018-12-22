package ru.icerebro.attedance_control.dao.interfaces;

import ru.icerebro.attedance_control.entities.Group;

public interface GroupDAO {
    Group getGroup(String name);
    int saveGroup(Group group);
    void deleteGroup(Group group);
    void updateGroup(Group group);
}
