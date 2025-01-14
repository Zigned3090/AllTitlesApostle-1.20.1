package z1gned.goetyrevelation.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class DefeatApollyonInNetherState extends SavedData {
    public boolean isDropped = false;

    public static DefeatApollyonInNetherState createNbt(CompoundTag nbt) {
        DefeatApollyonInNetherState state = new DefeatApollyonInNetherState();
        state.isDropped = nbt.getBoolean("ApollyonIsDropped");
        return state;
    }

    public void setDropped(boolean isDragonKilled) {
        this.isDropped = isDragonKilled;
    }

    public boolean isDropped() {
        return isDropped;
    }
    @Override
    public CompoundTag save(CompoundTag p_77763_) {
        p_77763_.putBoolean("ApollyonIsDropped", this.isDropped);
        return p_77763_;
    }

}
