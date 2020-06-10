package ru.icerebro.attedance_control.entities;

import java.util.Objects;

public class Dude {
    private int id;
    private Boolean control;
    private int key;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getControl() {
        return control;
    }

    public void setControl(Boolean control) {
        this.control = control;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dude dude = (Dude) o;
        return id == dude.id &&
                key == dude.key &&
                Objects.equals(control, dude.control);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, control, key);
    }
}
