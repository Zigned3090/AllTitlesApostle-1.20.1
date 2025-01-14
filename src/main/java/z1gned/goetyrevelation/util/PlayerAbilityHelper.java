package z1gned.goetyrevelation.util;

import com.Polarice3.Goety.common.entities.hostile.servants.ObsidianMonolith;

public interface PlayerAbilityHelper {

    int getInvulTick();

    void setInvulTick(int tick);

    int getMeteor();

    void setMeteor(int tick);

    int getMeteoring();

    void setMeteoring(int tick);

    void setMonolith(ObsidianMonolith obsidianMonolith);

    ObsidianMonolith getMonolith();

    void setSpin(float spin);

    float getSpin();

}
