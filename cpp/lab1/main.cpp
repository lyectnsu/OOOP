#include <iostream>
#include "TritSet.h"

int main() {
    trl::TritSet ts1(10);
    trl::TritSet ts2(20);

    ts1[10] = trl::False;

    ts2[20] = trl::Unknown;
    (ts2.operator[](20)).operator=(trl::Unknown);
    ts2[20] = ts1[10];
    std::cout << "TS1" << ts1 << "\n";
    std::cout << "TS2" << ts2 << "\n";

    return 0;
}
