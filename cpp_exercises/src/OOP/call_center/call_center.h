
#include "employee.h"
#include "call.h"
#include <queue>
#include <set>
#include <map>

using namespace std;

typedef string caller_name;

class CallCenter
{
    queue<Employee*> freeRespondants;
    set<Employee*> busyRespondants;
    queue<Call> tier1Calls;

    queue<Employee*> freeManagers;
    set<Employee*> busyManagers;
    queue<Call> tier2Calls;

    queue<Employee*> freeDirectors;
    set<Employee*> busyDirectors;
    queue<Call> tier3Calls;

    map<CallPriority, queue<Call>> callsOnHold;

    map<caller_name, Call> callsOngoing;

    public:
        CallCenter(int numberRespondants, int numberManagers, int numberDirectors)
        {
            for (int i = 0; i < numberRespondants; ++i)
            {
                freeRespondants.push(new Respondant(this));
            }
            for (int i = 0; i < numberManagers; ++i)
            {
                freeManagers.push(new Manager(this));
            }
            for (int i = 0; i < numberDirectors; ++i)
            {
                freeDirectors.push(new Director(this));
            }

            callsOnHold.insert({RESPONDANT, queue<Call>()});
            callsOnHold.insert({MANAGER, queue<Call>()});
            callsOnHold.insert({DIRECTOR, queue<Call>()});
        }

        void printStatus()
        {
            cout << "freeRespondants=" << freeRespondants.size() << " - ";
            cout << "freeManagers=" << freeManagers.size() << " - ";
            cout << "freeDirectors=" << freeDirectors.size() << endl;
        }

        ~CallCenter()
        {
            // TODO
        }

        void incomingCall(string caller)
        {
            cout << "incoming call from [" << caller << "]" << endl;
            dispatchCall(Call(caller));
        }

        void finishCall(caller_name name)
        {
            auto find_it = callsOngoing.find(name);
            if (find_it == callsOngoing.end())
            {
                cout << "not ongoing call for caller [" << name << "]" << endl;
                return;
            }

            Call finishingCall = find_it->second;
            cout << "finishing call from [" << name << "], releasing employee [" << finishingCall.getHandler()->whoAmi() << "]" << endl;

            if (finishingCall.getPriority() == RESPONDANT)
            {
                releaseRespondant(finishingCall.getHandler());
            } else if (finishingCall.getPriority() == MANAGER)
            {
                releaseManager(finishingCall.getHandler());
            } else if (finishingCall.getPriority() == DIRECTOR)
            {
                releaseDirector(finishingCall.getHandler());
            }
        }

        void escalateCall(Employee * employee)
        {
            // TODO
        }
    private:
        void dispatchCall(Call call)
        {
            if (!freeRespondants.empty())
            {
                assignHandler(call, freeRespondants);
            } else if (!freeManagers.empty())
            {
                assignHandler(call, freeManagers);
            } else if (!freeDirectors.empty())
            {
                assignHandler(call, freeDirectors);
            } else
            {
                std::cout << "All employees are busy assisting others, placing you on hold" << endl;
                putOnHold(call);
            }
        }

        void assignHandler(Call c, queue<Employee*> & freeEmployees)
        {
            Employee* freeEmployee = freeEmployees.front();
            freeEmployees.pop();
            c.assignHandler(freeEmployee);
            busyRespondants.insert(freeEmployee);

            callsOngoing.insert({c.getCaller(), c});
            cout << "call from [" << c.getCaller() << "] assigned to [" << freeEmployee->whoAmi() << "]" << endl;
        }

        void releaseRespondant(Employee * employee)
        {
            employee->finishTask();
            busyRespondants.erase(employee);
            freeRespondants.push(employee);
            reviewCallsOnHold();
        }

        void releaseManager(Employee * employee)
        {
            employee->finishTask();
            busyManagers.erase(employee);
            freeManagers.push(employee);
            reviewCallsOnHold();
        }

        void releaseDirector(Employee * employee)
        {
            employee->finishTask();
            busyDirectors.erase(employee);
            freeDirectors.push(employee);
            reviewCallsOnHold();
        }

        void reviewCallsOnHold()
        {
            auto respondantCallOnHold = callsOnHold.find(RESPONDANT);
            if (!respondantCallOnHold->second.empty() && !freeRespondants.empty())
            {
                Call & onHoldCall = respondantCallOnHold->second.front();
                assignHandler(onHoldCall, freeRespondants);
            }
            
            auto managerCallOnHold = callsOnHold.find(MANAGER);
            if (!managerCallOnHold->second.empty() && !freeManagers.empty())
            {
                Call & onHoldCall = managerCallOnHold->second.front();
                assignHandler(onHoldCall, freeManagers);
            }

            auto directorCallOnHold = callsOnHold.find(DIRECTOR);
            if (!directorCallOnHold->second.empty() && !freeDirectors.empty())
            {
                Call & onHoldCall = directorCallOnHold->second.front();
                assignHandler(onHoldCall, freeDirectors);
            }
        }

        void putOnHold(Call c)
        {
            auto find_it = callsOnHold.find(c.getPriority());
            if (find_it != callsOnHold.end())
            {
                find_it->second.push(c);
            } else
            {
                cout << "unexpected error, coulnt find queue of onHold calls for priority [" << c.getPriority() << "]" << endl;
            }
        }
};