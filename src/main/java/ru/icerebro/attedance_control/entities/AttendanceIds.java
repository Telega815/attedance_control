package ru.icerebro.attedance_control.entities;

public class AttendanceIds {
    private int id;
    private long lastwrittenid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastwrittenid() {
        return lastwrittenid;
    }

    public void setLastwrittenid(long lastwrittenid) {
        this.lastwrittenid = lastwrittenid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttendanceIds that = (AttendanceIds) o;

        if (id != that.id) return false;
        if (lastwrittenid != that.lastwrittenid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (lastwrittenid ^ (lastwrittenid >>> 32));
        return result;
    }
}
