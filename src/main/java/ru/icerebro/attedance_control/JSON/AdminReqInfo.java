package ru.icerebro.attedance_control.JSON;

public class AdminReqInfo {
    private String newDepName;
    private int depId;
    private String surname;
    private String name;
    private String patronymic;
    private Integer key;

    public String getNewDepName() {
        return newDepName;
    }

    public void setNewDepName(String newDepName) {
        this.newDepName = newDepName;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
