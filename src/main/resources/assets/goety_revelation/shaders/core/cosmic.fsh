#version 150

#define M_PI 3.1415926535897932384626433832795

#moj_import <fog.glsl>

const int cosmiccount = 10;
const int cosmicoutof = 101;
const float lightmix = 0.2f;

uniform sampler2D Sampler0;
uniform sampler2D CosmicSampler0;
uniform sampler2D CosmicSampler1;
uniform sampler2D CosmicSampler2;
uniform sampler2D CosmicSampler3;
uniform sampler2D CosmicSampler4;
uniform sampler2D CosmicSampler5;
uniform sampler2D CosmicSampler6;
uniform sampler2D CosmicSampler7;
uniform sampler2D CosmicSampler8;
uniform sampler2D CosmicSampler9;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

uniform float time;

uniform float yaw;
uniform float pitch;
uniform float externalScale;

uniform float opacity;

uniform int hurt;

uniform vec4 cosmicuvs;
uniform vec4 cosmicuvs1;
uniform vec4 cosmicuvs2;
uniform vec4 cosmicuvs3;
uniform vec4 cosmicuvs4;
uniform vec4 cosmicuvs5;
uniform vec4 cosmicuvs6;
uniform vec4 cosmicuvs7;
uniform vec4 cosmicuvs8;
uniform vec4 cosmicuvs9;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;
in vec3 fPos;

out vec4 fragColor;

mat4 rotationMatrix(vec3 axis, float angle)
{

    axis = normalize(axis);
    float s = sin(angle);
    float c = cos(angle);
    float oc = 1.0 - c;

    return mat4(oc * axis.x * axis.x + c,           oc * axis.x * axis.y - axis.z * s,  oc * axis.z * axis.x + axis.y * s,  0.0,
    oc * axis.x * axis.y + axis.z * s,  oc * axis.y * axis.y + c,           oc * axis.y * axis.z - axis.x * s,  0.0,
    oc * axis.z * axis.x - axis.y * s,  oc * axis.y * axis.z + axis.x * s,  oc * axis.z * axis.z + c,           0.0,
    0.0,                                0.0,                                0.0,                                1.0);
}

void main (void)
{
    vec4 mask = texture(Sampler0, texCoord0.xy);

    float oneOverExternalScale = 1.0/externalScale;

    int uvtiles = 16;

    // background colour
    vec4 col = vec4(vec3(0.4, 0.0, 0.0), .8);

    if (hurt > 0) col = vec4(vec3(0.4, 0.4, 0.4), .8);

    float pulse = mod(time,400)/400.0;

    // get ray from camera to fragment
    vec4 dir = normalize(vec4(-fPos, 0));

    // rotate the ray to show the right bit of the sphere for the angle
    float sb = sin(pitch);
    float cb = cos(pitch);
    dir = normalize(vec4(dir.x, dir.y * cb - dir.z * sb, dir.y * sb + dir.z * cb, 0));

    float sa = sin(-yaw);
    float ca = cos(-yaw);
    dir = normalize(vec4(dir.z * sa + dir.x * ca, dir.y, dir.z * ca - dir.x * sa, 0));

    vec4 ray;

    // draw the layers
    for (int i=0; i<16; i++) {
        int mult = 16-i;

        // get semi-random stuff
        int j = i + 7;
        float rand1 = (j * j * 4321 + j * 8) * 2.0F;
        int k = j + 1;
        float rand2 = (k * k * k * 239 + k * 37) * 3.6F;
        float rand3 = rand1 * 347.4 + rand2 * 63.4;

        //MODIFIED BY MEGA
        // random rotation matrix by random rotation around random axis
        vec3 axis = normalize(vec3(sin(rand1), sin(rand2) , cos(rand3)));

        // apply
        //MODIFIED BY MEGA
        ray = dir * rotationMatrix(axis, mod(rand3, 2*M_PI));

        // calcuate the UVs from the final ray
        float rawu = 0.5 + (atan(ray.z,ray.x)/(2*M_PI));
        float rawv = 0.5 + (asin(ray.y)/M_PI);

        // get UV scaled for layers and offset by time;
        float scale = mult*0.5 + 2.75;
        float u = rawu * scale * externalScale;
        //float v = (rawv + time * 0.00006) * scale * 0.6;
        float v = (rawv + time * 0.0002 * oneOverExternalScale) * scale * 0.6 * externalScale;

        vec2 tex = vec2( u, v );

        // tile position of the current uv
        int tu = int(mod(floor(u*uvtiles),uvtiles));
        int tv = int(mod(floor(v*uvtiles),uvtiles));

        // get pseudorandom variants
        int position = ((171 * tu) + (489 * tv) + (303 * (i+31)) + 17209 );
        int rotation = int(mod(pow(tu,float(tv)) + tu + 3 + tv*i, 8));
        bool flip = false;
        if (rotation >= 4) {
            rotation -= 4;
            flip = true;
        }
        int symbol = int(mod(position, cosmicoutof));;
        // if it's an icon, then add the colour!
        if (symbol >= 0 && symbol < cosmiccount) {

            vec2 cosmictex = vec2(1.0,1.0);
            vec4 tcol = vec4(1.0,0.0,0.0,1.0);

            // get uv within the tile
            float ru = clamp(mod(u,1.0)*uvtiles - tu, 0.0, 1.0);
            float rv = clamp(mod(v,1.0)*uvtiles - tv, 0.0, 1.0);

            if (flip) {
                ru = 1.0 - ru;
            }

            float oru = ru;
            float orv = rv;

            // rotate uvs if necessary
            if (rotation == 1) {
                oru = 1.0-rv;
                orv = ru;
            } else if (rotation == 2) {
                oru = 1.0-ru;
                orv = 1.0-rv;
            } else if (rotation == 3) {
                oru = rv;
                orv = 1.0-ru;
            }

            // get the iicon uvs for the tile
            vec4 cosmicuv = vec4(0.0);
            if (symbol == 0)
                cosmicuv = cosmicuvs;
            if (symbol == 1)
                cosmicuv = cosmicuvs1;
            if (symbol == 2)
                cosmicuv = cosmicuvs2;
            if (symbol == 3)
                cosmicuv = cosmicuvs3;
            if (symbol == 4)
                cosmicuv = cosmicuvs4;
            if (symbol == 5)
                cosmicuv = cosmicuvs5;
            if (symbol == 6)
                cosmicuv = cosmicuvs6;
            if (symbol == 7)
                cosmicuv = cosmicuvs7;
            if (symbol == 8)
                cosmicuv = cosmicuvs8;
            if (symbol == 9)
                cosmicuv = cosmicuvs9;
            float umin = cosmicuv.x;
            float umax = cosmicuv.z;
            float vmin = cosmicuv.y;
            float vmax = cosmicuv.w;

            // interpolate based on tile uvs
            cosmictex.x = umin * (1.0-oru) + umax * oru;
            cosmictex.y = vmin * (1.0-orv) + vmax * orv;

            if (symbol == 0)
                tcol = texture(CosmicSampler0, cosmictex);
            if (symbol == 1)
                tcol = texture(CosmicSampler1, cosmictex);
            if (symbol == 2)
                tcol = texture(CosmicSampler2, cosmictex);
            if (symbol == 3)
                tcol = texture(CosmicSampler3, cosmictex);
            if (symbol == 4)
                tcol = texture(CosmicSampler4, cosmictex);
            if (symbol == 5)
                tcol = texture(CosmicSampler5, cosmictex);
            if (symbol == 6)
                tcol = texture(CosmicSampler6, cosmictex);
            if (symbol == 7)
                tcol = texture(CosmicSampler7, cosmictex);
            if (symbol == 8)
                tcol = texture(CosmicSampler8, cosmictex);
            if (symbol == 9)
                tcol = texture(CosmicSampler9, cosmictex);

            // set the alpha, blending out at the bunched ends
            float a = tcol.r * (0.5 + (1.0/mult) * 1.0) * (1.0-smoothstep(0.15, 0.48, abs(rawv-0.5)));

            // get fancy colours
            float r = (mod(rand1, 29.0)/29.0) * 0.3 + 0.4;
            float g = (mod(rand2, 35.0)/35.0) * 0.4 + 0.6;
            float b = (mod(rand1, 17.0)/17.0) * 0.3 + 0.7;

            // mix the colours
            //col = col*(1-a) + vec4(r,g,b,1)*a;
            col = col + vec4(r,g,b,1)*a;
        }
    }

    // apply lighting
    vec3 shade = vertexColor.rgb * (lightmix) + vec3(1.0-lightmix,1.0-lightmix,1.0-lightmix);
    col.rgb *= shade;

    // apply mask
    col.a *= mask.r * opacity;

    col = clamp(col,0.0,1.0);
    vec4 color = col * ColorModulator;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
