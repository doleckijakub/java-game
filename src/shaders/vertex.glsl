#version 400 core

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec3 in_color;

uniform float scale;

out vec3 color;

void main(void) {
    gl_Position = vec4(scale * in_position, 1.0f);
    color = in_color;
}