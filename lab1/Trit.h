#include <iostream>

namespace trl {

    enum TritValue {
        False = 0,
        Unknown = 1,
        True = 3
    };

    class Trit {
    public:
        TritValue value;

        Trit();
        Trit(TritValue tv);

        bool operator==(TritValue tv);
        Trit operator&(Trit ts);
        Trit operator|(Trit ts);
        Trit operator~();
    };
}

std::ostream &operator<<(std::ostream &os, trl::Trit &t);

std::ostream &operator<<(std::ostream &os, trl::TritValue tv);