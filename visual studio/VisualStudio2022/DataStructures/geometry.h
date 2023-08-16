#pragma once

#define _USE_MATH_DEFINES

#include <vector>
#include <math.h>

namespace geometry
{
    class PointInCircleGenerator
    {
    public:
        double inner_circle, outer_circle;
        PointInCircleGenerator(double inner_circle, double outer_cicle);
        
        std::pair<std::vector<double>, std::vector<double>> generate(size_t how_many);
    };

}
