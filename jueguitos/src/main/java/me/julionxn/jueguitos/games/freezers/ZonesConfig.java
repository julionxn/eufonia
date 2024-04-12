package me.julionxn.jueguitos.games.freezers;

import com.google.gson.annotations.Expose;
import me.julionxn.jueguitos.core.configs.SerializableJsonManager;

import java.util.HashMap;

public class ZonesConfig extends SerializableJsonManager<ZonesConfig> {

    @Expose
    private final HashMap<String, Zone> loadedZones = new HashMap<>();

    protected ZonesConfig() {
        super("freezers.json", 1.0f, ZonesConfig.class);
    }

    public HashMap<String, Zone> getZones(){
        return loadedZones;
    }

    @Override
    protected ZonesConfig getCurrentInstance() {
        return this;
    }

    @Override
    protected void afterLoad() {

    }

}
