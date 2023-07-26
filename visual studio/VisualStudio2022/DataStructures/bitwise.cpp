
#include <cmath>
#include "bitwise.h"



int mask(int A)
{
    return A & 1073741823;              // get rid of the higher order bits
}

bool conforms(int A, int B)
{
    return (A & B) == B;
}

int count_combinations(int mask)
{
    int result = 1;
    for (int i = 0; i < 30; i++)
    {
        if ((mask & 1) == 0)
        {
            result *= 2;
        }
        mask = mask >> 1;
    }
    return result;
}

int semi_brute_force(int A, int B, int C)
{
    int differences = 0;
    int sliding_A = A;
    int sliding_B = B;
    int sliding_C = C;
    int first_diff = -1;
    for (int i = 0; i < 30; i++)
    {
        int A_mask = sliding_A & 1;
        int B_mask = sliding_B & 1;
        int C_mask = sliding_C & 1;
        if (A_mask == B_mask && B_mask == C_mask)
        {
        }
        else
        {
            if (first_diff == -1)
            {
                first_diff = i;
            }

            ++differences;
        }
        sliding_A = sliding_A >> 1;
        sliding_B = sliding_B >> 1;
        sliding_C = sliding_C >> 1;
    }

    int mask_lower_bits = pow(2, first_diff) - 1;
    int A_mask = (A >> first_diff) & mask_lower_bits;
    int B_mask = (B >> first_diff) & mask_lower_bits;
    int C_mask = (C >> first_diff) & mask_lower_bits;

    // the masks are shifted to the very first bit where they differ
    int result = 0;
    for (int i = 0; i < mask_lower_bits + 1; ++i)
    {
        if (conforms(i, A_mask) || conforms(i, B_mask) || conforms(i, C_mask))
        {
            ++result;
        }
    }
    return result;
}

namespace bitwise
{
    int solution(int A, int B, int C)
    {
        // https://app.codility.com/programmers/trainings/9/count_conforming_bitmasks/

        return semi_brute_force(mask(A), mask(B), mask(C));
    }
}
