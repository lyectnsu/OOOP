#include <iostream>
#include "TritSet.h"

int main() {
    trl::TritSet ts1(10);
    trl::TritSet ts2(20);

    ts1[0] = trl::False;
    ts1[1] = trl::True;
    ts1[2] = trl::True;
    ts1[9] = trl::True;

    ts2[0] = trl::True;
    ts2[1] = trl::Unknown;
    ts2[2] = trl::True;

    trl::TritSet ts3 = ts1 & ts2;

    std::cout << ts1.getCapacity() << "\n";
    std::cout << ts2.getCapacity() << "\n";
    std::cout << ts3.getCapacity() << "\n";

    std::cout << ts1 << "\n";
    std::cout << ts2 << "\n";
    std::cout << ts3 << "\n";

    std::cout << ts3.cardinality(trl::Unknown) << "\n";

    auto cardinalities = ts3.cardinality();
    for (auto p: cardinalities){
        std::cout << "[ " << p.first << " ] : " << p.second << "\n";
    }

    ts3[1] = trl::False;
    ts1[9] = ts1[3];
    std::cout << "!!!" << ts1[9] << "!!!\n";
    ts1[9] = ts3[1];
    std::cout << "!!!" << ts1[9] << "!!!\n";
    cardinalities = ts3.cardinality();
    for (auto p: cardinalities){
        std::cout << "[ " << p.first << " ] : " << p.second << "\n";
    }
    return 0;
}
