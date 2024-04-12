package me.julionxn.jueguitos.games.freezers;

import com.google.gson.annotations.Expose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Zone {

    public final String teamId;
    @Expose
    public final int[] from;
    @Expose
    public final int[] to;

    public Zone(String teamId, int[] from, int[] to){
        this.teamId = teamId;
        this.from = from;
        this.to = to;
    }

    public Vec3d from(){
        return new Vec3d(from[0], from[1], from[2]);
    }

    public Vec3d to(){
        return new Vec3d(to[0], to[1], to[2]);
    }

    public boolean isInside(PlayerEntity player){
        Vec3d pos = player.getPos();
        Box box = new Box(from(), to());
        return box.contains(pos);
    }

}
