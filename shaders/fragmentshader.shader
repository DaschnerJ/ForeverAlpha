#version 120

uniform float red;
uniform float green;
uniform float blue;

void main() {
    gl_FragColor = vec4(1,red,green,blue);
}