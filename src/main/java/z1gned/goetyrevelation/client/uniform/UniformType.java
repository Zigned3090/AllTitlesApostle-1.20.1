package z1gned.goetyrevelation.client.uniform;

import com.mojang.blaze3d.shaders.Uniform;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum UniformType {
    INT(UniformType.Carrier.INT, 1),
    U_INT(UniformType.Carrier.U_INT, 1),
    FLOAT(UniformType.Carrier.FLOAT, 1),
    VEC2(UniformType.Carrier.FLOAT, 2),
    I_VEC2(UniformType.Carrier.INT, 2),
    U_VEC2(UniformType.Carrier.U_INT, 2),
    B_VEC2(UniformType.Carrier.INT, 2),
    VEC3(UniformType.Carrier.FLOAT, 3),
    I_VEC3(UniformType.Carrier.INT, 3),
    U_VEC3(UniformType.Carrier.U_INT, 3),
    B_VEC3(UniformType.Carrier.INT, 3),
    VEC4(UniformType.Carrier.FLOAT, 4),
    I_VEC4(UniformType.Carrier.INT, 4),
    U_VEC4(UniformType.Carrier.U_INT, 4),
    B_VEC4(UniformType.Carrier.INT, 4),
    MAT2(UniformType.Carrier.MATRIX, 4),
    MAT2x3(UniformType.Carrier.MATRIX, 6),
    MAT2x4(UniformType.Carrier.MATRIX, 8),
    MAT3(UniformType.Carrier.MATRIX, 9),
    MAT3x2(UniformType.Carrier.MATRIX, 6),
    MAT3x4(UniformType.Carrier.MATRIX, 12),
    MAT4(UniformType.Carrier.MATRIX, 16),
    MAT4x2(UniformType.Carrier.MATRIX, 8),
    MAT4x3(UniformType.Carrier.MATRIX, 12),
    DOUBLE(UniformType.Carrier.DOUBLE, 1),
    D_VEC2(UniformType.Carrier.DOUBLE, 2),
    D_VEC3(UniformType.Carrier.DOUBLE, 3),
    D_VEC4(UniformType.Carrier.DOUBLE, 4),
    D_MAT2(UniformType.Carrier.D_MATRIX, 4),
    D_MAT2x3(UniformType.Carrier.D_MATRIX, 6),
    D_MAT2x4(UniformType.Carrier.D_MATRIX, 8),
    D_MAT3(UniformType.Carrier.D_MATRIX, 9),
    D_MAT3x2(UniformType.Carrier.D_MATRIX, 6),
    D_MAT3x4(UniformType.Carrier.D_MATRIX, 12),
    D_MAT4(UniformType.Carrier.D_MATRIX, 16),
    D_MAT4x2(UniformType.Carrier.D_MATRIX, 8),
    D_MAT4x3(UniformType.Carrier.D_MATRIX, 12);

    public static final UniformType[] VALUES = values();
    private final UniformType.Carrier carrier;
    private final int size;

    UniformType(UniformType.Carrier carrier, int size) {
        this.carrier = carrier;
        this.size = size;
    }

    public static @Nullable UniformType parse(String s) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "matrix2x2":
                return MAT2;
            case "matrix3x3":
                return MAT3;
            case "matrix4x4":
                return MAT4;
            default:
                UniformType[] var1 = VALUES;
                int var2 = var1.length;

                for (int var3 = 0; var3 < var2; ++var3) {
                    UniformType value = var1[var3];
                    String n = value.name().toLowerCase(Locale.ROOT);
                    if (n.equals(s)) {
                        return value;
                    }
                }

                return null;
        }
    }

    public UniformType.Carrier getCarrier() {
        return this.carrier;
    }

    public int getSize() {
        return this.size;
    }

    public int getVanillaType() {
        byte var10000;
        switch (this) {
            case INT:
            case U_INT:
                var10000 = 0;
                break;
            case FLOAT:
                var10000 = 4;
                break;
            case VEC2:
                var10000 = 5;
                break;
            case I_VEC2:
            case U_VEC2:
            case B_VEC2:
                var10000 = 1;
                break;
            case VEC3:
                var10000 = 6;
                break;
            case I_VEC3:
            case U_VEC3:
            case B_VEC3:
                var10000 = 2;
                break;
            case VEC4:
                var10000 = 7;
                break;
            case I_VEC4:
            case U_VEC4:
            case B_VEC4:
                var10000 = 3;
                break;
            case MAT2:
                var10000 = 8;
                break;
            case MAT3:
                var10000 = 9;
                break;
            case MAT4:
                var10000 = 10;
                break;
            default:
                var10000 = -1;
        }

        return var10000;
    }

    public enum Carrier {
        INT,
        U_INT,
        FLOAT,
        DOUBLE,
        MATRIX,
        D_MATRIX;

        Carrier() {
        }
    }
}
