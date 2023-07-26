// Multithreading.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <thread>
#include <mutex>
#include <sstream>

std::mutex stdout_mutex;

void print_stdout(std::string msg)
{
    std::lock_guard<std::mutex> guard(stdout_mutex);
    std::cout << msg << std::endl;
}

void sum(int a, int b)
{
    size_t i = 0;
    while (i++ < 10)
    {
        std::stringstream ss;
        ss << "sum of numbers" << a << " and " << b;
        print_stdout(ss.str());       
    }
}

void test_threads()
{
    std::cout << "Starting" << std::endl;

    std::thread t1(sum, 1, 2);
    std::thread t2(sum, 2, 6);
    std::thread t3(sum, 1, 9);
    std::thread t4(sum, 4, 1);
    std::thread t5(sum, 9, 22);

    t1.join();
    t2.join();
    t3.join();
    t4.join();
    t5.join();

    std::cout << "Finished" << std::endl;
}


