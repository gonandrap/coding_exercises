#ifndef __EMPLOYEE_H__
#define __EMPLOYEE_H__

#include <iostream>

using namespace std;
class CallCenter;

class Employee
{
    bool busy;
    CallCenter * organization;

    public:
        Employee(CallCenter* org) : busy(false), organization(org) {}

        virtual string whoAmi() = 0;

        bool isBusy()
        {
            return busy;
        }

        void assignTask()
        {
            busy = true;
        }

        void finishTask()
        {
            busy = false;
        }

        void escalateTask();
};

class Respondant : public Employee
{
    public:
        Respondant(CallCenter* org) : Employee(org)
        {}

        string whoAmi()
        {
            return "Respondant";
        }
};

class Manager : public Employee
{
    public:
        Manager(CallCenter* org) : Employee(org)
        {}

        string whoAmi()
        {
            return "Manager";
        }
};

class Director : public Employee
{
    public:
        Director(CallCenter* org) : Employee(org)
        {}

        string whoAmi()
        {
            return "Director";
        }
};

#endif