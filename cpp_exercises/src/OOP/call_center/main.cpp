
#include "call_center.h"

int main(int argc, char* argv[])
{
    CallCenter callCenter(5, 2, 1);
    callCenter.incomingCall("Gonzalo");
    callCenter.incomingCall("Martin");
    callCenter.incomingCall("Bob");
    callCenter.incomingCall("Tito");
    callCenter.incomingCall("Rob");
    callCenter.incomingCall("Susana");
    callCenter.incomingCall("Tincho");
    callCenter.incomingCall("Pablo");
    callCenter.incomingCall("Mica");

    callCenter.finishCall(caller_name("Susana"));

    callCenter.printStatus();

}