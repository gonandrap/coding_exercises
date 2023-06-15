
enum CallPriority {RESPONDANT, MANAGER, DIRECTOR};

#include <string>
#include "employee.h"

using namespace std;

class Call
{
    CallPriority priority;
    string caller;
    Employee* handler;

    public:
        Call(string callerName) : priority(CallPriority::RESPONDANT), caller(callerName), handler(nullptr)
        {

        }

        CallPriority getPriority()
        {
            return priority;
        }

        Employee* getHandler()
        {
            return handler;
        }

        string getCaller()
        {
            return caller;
        }

        void assignHandler(Employee* employee)
        {
            handler = employee;
        }
};