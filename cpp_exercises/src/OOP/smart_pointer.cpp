
#include <iostream>
#include "singleton.h"

template <class T>
class MySmartPointer
{
    T * actualPointer = nullptr;
    static int counter;

    public:
        MySmartPointer(T* instance) : actualPointer(instance)
        {
            counter++;
            std::cout << "creating an [actualPointer] instance" << std::endl;
        }

        MySmartPointer(MySmartPointer & instance)
        {
            counter++;
            std::cout << "creating a [MySmartPointer] copy, references=" << counter << std::endl;
        }

        ~MySmartPointer()
        {
            counter--;
            if (counter == 0)
            {
                std::cout << "deleting [actualPointer], [" << counter << "] remaining" << std::endl;
                delete actualPointer;
            } else
            {
                std::cout << "counter=[" << counter << "], not yet deleting actual pointer" << std::endl;
            }
        }

        T* operator->() { return actualPointer; }

        MySmartPointer & operator=(MySmartPointer & instance)
        {
            counter++;
            std::cout << "[MySmartPointer] assign operator, references=" << counter << std::endl;
        }

};

class House
{
    public:
        House()
        {
            std::cout << "creating a [House]" << std::endl;
        }

        House(House &)
        {
            std::cout << "creating a copy of [House]" << std::endl;
        }

        ~House()
        {
            std::cout << "deleting an instance of [House]" << std::endl;
        }

        void hello(void)
        {
            std::cout << "house implementation!" << std::endl;
        }
};


int main_singleton(int argc, char* argv[])
{
    House * house = Singleton<House>::getInstance();
    House * house_2 = Singleton<House>::getInstance();
    House * house_3 = Singleton<House>::getInstance();
    house->hello();
    house_2->hello();
    house_3->hello();
}

template <class T>
T* Singleton<T>::instance = nullptr;


template<class T>
int MySmartPointer<T>::counter = 0;


int main(int argc, char* argv[])
{
    MySmartPointer<House> smart_ptr(new House());
    MySmartPointer<House> smart_ptr_2(smart_ptr);
    MySmartPointer<House> * smart_ptr_3;
    *smart_ptr_3 = smart_ptr;

    return 0;
}