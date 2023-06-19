
#include <time.h>
#include "util.h"
#include <iostream>
#include <chrono>
#include <ctime>
#include <iomanip>


int days_between_dates(time_t now, time_t before)
{
    // FIXME : next two lines are useless, since the std returns the same value for both (even though "now" and "before" have diff values)
    tm * nowTm = localtime(&now);
    tm * beforeTm = localtime(&before);

    /*
        I could calculate based on seconds elapsed, but the idea is to get familiar with the "tm" struct.
    */
    if (nowTm->tm_year == beforeTm->tm_year)
    {
        return nowTm->tm_yday - beforeTm->tm_yday;
    } else if (nowTm->tm_year - beforeTm->tm_year == 1)
    {
        return nowTm->tm_yday + (DAYS_PER_YEAR - beforeTm->tm_yday);
    } else if (nowTm->tm_year - beforeTm->tm_year > 1)
    {
        return ((nowTm->tm_year - beforeTm->tm_year - 1) * DAYS_PER_YEAR) + // days for each "complete" year that has passed
                nowTm->tm_yday +       // days of current year
                (DAYS_PER_YEAR - beforeTm->tm_yday);    // days of first year
    } else
    {
        std::cout << "not supported operation" << std::endl;
    }
}


int main(int argc, char* argv[])
{
    time_t now_time = time(0);
    time_t before = getDateFromPast(1, 1, 37, 4, 3, 22);
    int daysElapsed = days_between_dates(now_time, before);
    std::cout << "daysElapsed=" << daysElapsed << std::endl;
    std::time_t diff = (std::time_t) difftime(now_time, before);
    std::cout << "difftime=" << diff <<  std::endl;
    
    const auto system_clock_now = std::chrono::system_clock::now();
    const std::time_t t_c = std::chrono::system_clock::to_time_t(system_clock_now);
    std::cout << "system time=" << std::ctime(&t_c) << std::endl;

    std::time_t now = std::time(nullptr);
    std::tm tm = *std::localtime(&now);

    std::cout << "time now  = " << std::put_time(&tm, "%F %T") << std::endl;

    return 0;
}