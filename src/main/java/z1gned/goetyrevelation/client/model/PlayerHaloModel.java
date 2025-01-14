package z1gned.goetyrevelation.client.model;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import z1gned.goetyrevelation.util.PlayerAbilityHelper;

public class PlayerHaloModel<T extends AbstractClientPlayer> extends PlayerModel<T> {
    public ModelPart halo = this.getHead().getChild("halo");
    public ModelPart halo1;

    public PlayerHaloModel(ModelPart p_170821_, boolean p_170822_) {
        super(p_170821_, p_170822_);
        this.halo1 = this.halo.getChild("halo1");
    }

    public static LayerDefinition createBodyLayer(CubeDeformation p_170536_) {
        MeshDefinition meshdefinition = PlayerModel.createMesh(p_170536_, true);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.getChild("head");
        PartDefinition halo = head.addOrReplaceChild("halo", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -12.0F, 5.0F, 0.7854F, 0.0F, 0.0F));
        halo.addOrReplaceChild("halo1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(T p_103395_, float p_103396_, float p_103397_, float p_103398_, float p_103399_, float p_103400_) {
        super.setupAnim(p_103395_, p_103396_, p_103397_, p_103398_, p_103399_, p_103400_);
        this.halo1.zRot = ((PlayerAbilityHelper) p_103395_).getSpin();
    }

}
