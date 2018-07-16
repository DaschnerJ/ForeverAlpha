#version 120

attribute vec2 vertices;
attribute vec2 texture_coords;

uniform mat4 projection;

varying vec2 out_texture_coords;

void main() {
    gl_Position = projection * vec4(vertices, 0, 1);
    out_texture_coords = texture_coords;
}