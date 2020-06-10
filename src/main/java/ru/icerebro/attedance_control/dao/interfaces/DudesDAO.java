package ru.icerebro.attedance_control.dao.interfaces;

import ru.icerebro.attedance_control.entities.Dude;

import java.util.List;

public interface DudesDAO {
    Dude getDude(int id);

    Dude getDudeByKey(int key);

    List<Dude> getDudes();

    int saveDude(Dude dude);
    void deleteDude(Dude dude);
    void updateDude(Dude dude);
}
