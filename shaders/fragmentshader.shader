#version 120

uniform vec3 color;
uniform sampler2D sampler;

varying vec2 out_texture_coords;

void main() {
    gl_FragColor = texture2D(sampler, out_texture_coords) * color;
}