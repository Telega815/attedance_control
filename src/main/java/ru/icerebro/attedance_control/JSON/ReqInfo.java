package ru.icerebro.attedance_control.JSON;

public class ReqInfo {
    private boolean selectedEmployee;

    // DEPARTMENT
    private int depId;
    private int month;
    private int year;

    // EMPLOYEE
    private int empId;
    private int empKey;
    private int minDay;
    private int maxDay;
    private int minMonth;
    private int maxMonth;
    private int minYear;
    private int maxYear;

    public boolean isSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(boolean selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    // DEPARTMENT

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


    // EMPLOYEE

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getMinDay() {
        return minDay;
    }

    public void setMinDay(int minDay) {
        this.minDay = minDay;
    }

    public int getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public int getMinMonth() {
        return minMonth;
    }

    public void setMinMonth(int minMonth) {
        this.minMonth = minMonth;
    }

    public int getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(int maxMonth) {
        this.maxMonth = maxMonth;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public int getEmpKey() {
        return empKey;
    }

    public void setEmpKey(int empKey) {
        this.empKey = empKey;
    }
}
