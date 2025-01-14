package z1gned.goetyrevelation.goal;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.ZombieServant;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

public class MoveToApollyonGoal<T extends ZombieServant> extends Summoned.FollowOwnerGoal<T> {
    ZombieServant zombieServant;

    public MoveToApollyonGoal(T summonedEntity, double speed, float startDistance, float stopDistance) {
        super(summonedEntity, speed, startDistance, stopDistance);
        this.zombieServant = summonedEntity;
    }

    @Override
    public boolean canUse() {
        if (!super.canUse()) {
            return false;
        } else {
            return this.zombieServant.isBaby() && this.zombieServant.getTrueOwner() instanceof Apostle apostle && ((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isApollyon();
        }
    }

}
