
#include <iostream>
#include "hello_world.h"
#include <math.h>
#include <string.h>

int main(int argc, char* argv[])
{
    std::cout << "hello from main!" << std::endl;
    hello_world_1();
    hello_world_2();
    hello_world_3();

    std::cout << "result of calling sum(3,4)=" << sum(3,4) << std::endl;
    std::cout << "result of calling contac('hola,', 'mundo!')=" << string_example::concat("hola,", "mundo!") << std::endl;
}