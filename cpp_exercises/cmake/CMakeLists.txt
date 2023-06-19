cmake_minimum_required(VERSION 3.10.0)

#project name
project (hello_cmake)

set (string_include "/home/gonzalo/workspace/coding_exercises/cpp_exercises/cmake/library/static/string/include"
    CACHE PATH "Location of static library string")

set (math_include "/home/gonzalo/workspace/coding_exercises/cpp_exercises/cmake/headers_libraries"
    CACHE PATH "Location of public headers")

set (math_lib "/home/gonzalo/workspace/coding_exercises/cpp_exercises/cmake/library/static/string/build-unix/libstring_library.a")

include_directories(${math_include} ${string_include})

file (GLOB sourcefiles 
        "headers_libraries/*.h"
        "src/*.h"
        "src/*.cpp")

add_executable(hello_main ${sourcefiles})

target_link_libraries(hello_main ${math_lib})