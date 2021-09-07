#include "module1.h"
#include "module2.h"
#include <iostream>

namespace A {
    void f(int) {std::cout << "int\n";}
}

  // f is a synonym for A::f(int) only

namespace A {
    void f(char) {std::cout << "char\n";}
}

void b() {
    using A::f;   // refers to A::f(int) AND A::f(char)
    f(1);   // calls A::f(char);
}

int main(int argc, char** argv)
{
    b();
    std::cout <<  "Hello world!" << "\n";

    std::cout << Module1::getMyName() << "\n";
    std::cout << Module2::getMyName() << "\n";

    using namespace Module1;
    std::cout << getMyName() << "\n"; // (A)
    std::cout << Module2::getMyName() << "\n";

    // using namespace Module2; // (B)
    // std::cout << getMyName() << "\n"; // COMPILATION ERROR (C)

    using Module2::getMyName;
    std::cout << getMyName() << "\n"; // (D)
}
