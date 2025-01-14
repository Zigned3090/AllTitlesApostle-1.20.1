package z1gned.goetyrevelation.client.shader.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import z1gned.goetyrevelation.client.event.NetherStarShaders;
import z1gned.goetyrevelation.client.uniform.ATAUniform;
import z1gned.goetyrevelation.client.uniform.UniformType;
import z1gned.goetyrevelation.client.util.NetherStarTextures;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class NetherStarShaderInstance extends ShaderInstance {
    private final List<Runnable> applyCallbacks = new LinkedList<>();

    protected NetherStarShaderInstance(ResourceProvider resourceProvider, ResourceLocation shaderLocation, VertexFormat format) throws IOException {
        super(resourceProvider, shaderLocation, format);

    }

    public static NetherStarShaderInstance create(ResourceProvider resourceProvider, ResourceLocation loc, VertexFormat format) {
        try {
            return new NetherStarShaderInstance(resourceProvider, loc, format);
        } catch (IOException var4) {
            throw new RuntimeException("Failed to initialize shader.", var4);
        }
    }

    private static float[] parseFloats(int count, JsonArray jsonValues) throws ChainedJsonException {
        int i = 0;
        float[] values = new float[Math.max(count, 16)];
        Iterator var4 = jsonValues.iterator();

        while (var4.hasNext()) {
            JsonElement jsonValue = (JsonElement) var4.next();

            try {
                values[i++] = GsonHelper.convertToFloat(jsonValue, "value");
            } catch (Exception var8) {
                ChainedJsonException chainedjsonexception = ChainedJsonException.forException(var8);
                chainedjsonexception.prependJsonKey("values[" + i + "]");
                throw chainedjsonexception;
            }
        }

        if (count > 1 && jsonValues.size() == 1) {
            Arrays.fill(values, 1, values.length, values[0]);
        }

        return Arrays.copyOfRange(values, 0, count);
    }

    private static int[] parseInts(int count, JsonArray jsonValues) throws ChainedJsonException {
        int i = 0;
        int[] values = new int[Math.max(count, 16)];
        Iterator var4 = jsonValues.iterator();

        while (var4.hasNext()) {
            JsonElement jsonValue = (JsonElement) var4.next();

            try {
                values[i++] = GsonHelper.convertToInt(jsonValue, "value");
            } catch (Exception var8) {
                ChainedJsonException chainedjsonexception = ChainedJsonException.forException(var8);
                chainedjsonexception.prependJsonKey("values[" + i + "]");
                throw chainedjsonexception;
            }
        }

        if (count > 1 && jsonValues.size() == 1) {
            Arrays.fill(values, 1, values.length, values[0]);
        }

        return Arrays.copyOfRange(values, 0, count);
    }

    private static double[] parseDoubles(int count, JsonArray jsonValues) throws ChainedJsonException {
        int i = 0;
        double[] values = new double[Math.max(count, 16)];
        Iterator var4 = jsonValues.iterator();

        while (var4.hasNext()) {
            JsonElement jsonValue = (JsonElement) var4.next();

            try {
                values[i++] = GsonHelper.convertToDouble(jsonValue, "value");
            } catch (Exception var8) {
                ChainedJsonException chainedjsonexception = ChainedJsonException.forException(var8);
                chainedjsonexception.prependJsonKey("values[" + i + "]");
                throw chainedjsonexception;
            }
        }

        if (count > 1 && jsonValues.size() == 1) {
            Arrays.fill(values, 1, values.length, values[0]);
        }

        return Arrays.copyOfRange(values, 0, count);
    }

    public void onApply(Runnable callback) {
        this.applyCallbacks.add(callback);
    }

    public void apply() {
        Iterator var1 = this.applyCallbacks.iterator();

        while (var1.hasNext()) {
            Runnable callback = (Runnable) var1.next();
            callback.run();
        }

        super.apply();
    }

    public @Nullable ATAUniform getUniform(@NotNull String name) {
        return (ATAUniform) super.getUniform(name);
    }

    public void parseUniformNode(@NotNull JsonElement json) throws ChainedJsonException {
        JsonObject obj = GsonHelper.convertToJsonObject(json, "uniform");
        String name = GsonHelper.getAsString(obj, "name");
        String typeStr = GsonHelper.getAsString(obj, "type");
        UniformType type = UniformType.parse(typeStr);
        if (type == null) {
            throw new ChainedJsonException("Invalid type '%s'. See UniformType enum. All vanilla types supported.".formatted(typeStr));
        } else {
            int count;
            count = GsonHelper.getAsInt(obj, "count");
            label45:
            switch (type) {
                case FLOAT:
                    switch (count) {
                        case 2:
                            type = UniformType.VEC2;
                            break label45;
                        case 3:
                            type = UniformType.VEC3;
                            break label45;
                        case 4:
                            type = UniformType.VEC4;
                        default:
                            break label45;
                    }
                case INT:
                    switch (count) {
                        case 2:
                            type = UniformType.I_VEC2;
                            break label45;
                        case 3:
                            type = UniformType.I_VEC3;
                            break label45;
                        case 4:
                            type = UniformType.I_VEC4;
                        default:
                            break label45;
                    }
                case U_INT:
                    switch (count) {
                        case 2:
                            type = UniformType.U_VEC2;
                            break;
                        case 3:
                            type = UniformType.U_VEC3;
                            break;
                        case 4:
                            type = UniformType.U_VEC4;
                    }
            }

            ATAUniform uniform = ATAUniform.makeUniform(name, type, count, this);
            JsonArray jsonValues = GsonHelper.getAsJsonArray(obj, "values");
            if (jsonValues.size() != count && jsonValues.size() > 1) {
                throw new ChainedJsonException("Invalid amount of values specified (expected " + count + ", found " + jsonValues.size() + ")");
            } else {
                switch (type.getCarrier()) {
                    case INT:
                    case U_INT:
                        uniform.glUniformI(parseInts(count, jsonValues));
                        break;
                    case FLOAT:
                    case MATRIX:
                        uniform.glUniformF(false, parseFloats(count, jsonValues));
                        break;
                    case DOUBLE:
                    case D_MATRIX:
                        uniform.glUniformD(false, parseDoubles(count, jsonValues));
                }

                this.uniforms.add(uniform);
            }
        }
    }

    public void setSampler(int index, ResourceLocation texture) {
        TextureAtlasSprite atlasSprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
        super.setSampler("CosmicSampler" + index, Minecraft.getInstance().getTextureManager().getTexture(atlasSprite.atlasLocation()).getId());
        NetherStarShaders.COSMIC_UVS[index * 4] = atlasSprite.getU0();
        NetherStarShaders.COSMIC_UVS[index * 4 + 1] = atlasSprite.getV0();
        NetherStarShaders.COSMIC_UVS[index * 4 + 2] = atlasSprite.getU1();
        NetherStarShaders.COSMIC_UVS[index * 4 + 3] = atlasSprite.getV1();
    }

    public void setCosmicIcon() {
        for (int i = 0; i < 10; i++) {
            setSampler(i, NetherStarTextures.rl(i));
            float[] uvs = new float[]{NetherStarShaders.COSMIC_UVS[i * 4], NetherStarShaders.COSMIC_UVS[i * 4 + 1], NetherStarShaders.COSMIC_UVS[i * 4 + 2], NetherStarShaders.COSMIC_UVS[i * 4 + 3]};
            RenderSystem.applyModelViewMatrix();
            switch (i) {
                case 0:
                    if (NetherStarShaders.cosmicUVs != null) NetherStarShaders.cosmicUVs.set(uvs);
                case 1:
                    if (NetherStarShaders.cosmicUVs1 != null) NetherStarShaders.cosmicUVs1.set(uvs);
                case 2:
                    if (NetherStarShaders.cosmicUVs2 != null) NetherStarShaders.cosmicUVs2.set(uvs);
                case 3:
                    if (NetherStarShaders.cosmicUVs3 != null) NetherStarShaders.cosmicUVs3.set(uvs);
                case 4:
                    if (NetherStarShaders.cosmicUVs4 != null)  NetherStarShaders.cosmicUVs4.set(uvs);
                case 5:
                    if (NetherStarShaders.cosmicUVs5 != null)  NetherStarShaders.cosmicUVs5.set(uvs);
                case 6:
                    if (NetherStarShaders.cosmicUVs6 != null) NetherStarShaders.cosmicUVs6.set(uvs);
                case 7:
                    if (NetherStarShaders.cosmicUVs7 != null) NetherStarShaders.cosmicUVs7.set(uvs);
                case 8:
                    if (NetherStarShaders.cosmicUVs8 != null) NetherStarShaders.cosmicUVs8.set(uvs);
                case 9:
                    if (NetherStarShaders.cosmicUVs9 != null) NetherStarShaders.cosmicUVs9.set(uvs);
            }
        }

    }

}
