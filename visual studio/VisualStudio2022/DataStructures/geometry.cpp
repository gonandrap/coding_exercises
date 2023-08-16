
#include "geometry.h"

using namespace geometry;

PointInCircleGenerator::PointInCircleGenerator(double inner_circle, double outer_cicle) : inner_circle(inner_circle), outer_circle(outer_cicle)
{ }

std::pair<std::vector<double>, std::vector<double>> PointInCircleGenerator::generate(size_t how_many)
{
    std::vector<double> points_x, points_y;
    while (how_many > 0)
    {
        double  ang = (double) rand() / RAND_MAX * 2 * M_PI;
        double hyp = sqrt((double) rand() / RAND_MAX) * this->outer_circle;
        double adj = cos(ang) * hyp;
        double opp = sin(ang) * hyp;
        
        points_x.push_back(adj);
        points_y.push_back(opp);
        
        how_many -= 1;
    }
    return std::make_pair(points_x, points_y);
}