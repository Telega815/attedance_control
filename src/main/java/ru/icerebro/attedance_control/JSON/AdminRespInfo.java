package ru.icerebro.attedance_control.JSON;

import java.util.Map;

public class AdminRespInfo {
    private Map<Integer, String> deps;

    public Map<Integer, String> getDeps() {
        return deps;
    }

    public void setDeps(Map<Integer, String> deps) {
        this.deps = deps;
    }
}
