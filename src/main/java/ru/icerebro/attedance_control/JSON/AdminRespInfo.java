package ru.icerebro.attedance_control.JSON;

import java.util.Map;

public class AdminRespInfo {
    private Map<Integer, String> deps;
    private Map<Long, String> attendance;

    public Map<Integer, String> getDeps() {
        return deps;
    }

    public void setDeps(Map<Integer, String> deps) {
        this.deps = deps;
    }

    public Map<Long, String> getAttendance() {
        return attendance;
    }

    public void setAttendance(Map<Long, String> attendance) {
        this.attendance = attendance;
    }
}
