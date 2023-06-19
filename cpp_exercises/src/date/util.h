
#include <ctime>

const long SECS_PER_DAY = 86400;
const long SECS_PER_HOUR = 3600;
const long SECS_PER_MINUTE = 60;
const long DAYS_PER_YEAR = 365;
const long DAYS_PER_MONTH = 31;
const long SECS_PER_YEAR = DAYS_PER_YEAR * SECS_PER_DAY;
const long SECS_PER_MONTH = SECS_PER_DAY * DAYS_PER_MONTH;


time_t getDateFromPast(long numberYears, long numberMonths, long numberDays, long numberHours, long numberMinutes, long numberSeconds)
{
    time_t now = time(0);
    return now - (SECS_PER_YEAR * numberYears +
                  SECS_PER_MONTH * numberMonths +
                  SECS_PER_DAY * numberDays +
                  SECS_PER_HOUR * numberHours +
                  SECS_PER_MINUTE * numberMinutes +
                  numberSeconds);
}