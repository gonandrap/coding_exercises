cmake_minimum_required(VERSION 3.10.0)

project("StringLibrary")

file (GLOB sourcefiles 
        "src/*.h"
        "src/*.cpp")

add_library(string_library STATIC ${sourcefiles})