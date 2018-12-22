package ru.icerebro.attedance_control.JSON;

public class ReqInfo {
    private boolean selectedEmployee;
    private int depId;
    private int month;
    private int year;

    public boolean isSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(boolean selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
