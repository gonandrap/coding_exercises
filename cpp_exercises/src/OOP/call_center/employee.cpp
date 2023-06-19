
#include "employee.h"
#include "call_center.h"

void Employee::escalateTask()
{
    // I wasn't sure where to put this, but
    organization->escalateCall(this);
}