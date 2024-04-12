package me.julionxn.jueguitos.games.freezers;

import com.google.gson.annotations.Expose;
import me.julionxn.jueguitos.core.configs.SerializableJsonManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ZonesConfig extends SerializableJsonManager<ZonesConfig> {

    @Expose
    private final List<Zone> loadedZones = new ArrayList<>();
    private final HashMap<String, Zone> zones = new HashMap<>();

    protected ZonesConfig() {
        super("freezers", 1.0f, ZonesConfig.class);
    }

    public HashMap<String, Zone> getZones(){
        return zones;
    }

    @Override
    protected ZonesConfig getCurrentInstance() {
        return this;
    }

    @Override
    protected void afterLoad() {
        loadedZones.forEach(zone ->
            zones.put(zone.teamId, zone)
        );
    }

}
