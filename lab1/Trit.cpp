#include "Trit.h"

namespace trl {
    Trit::Trit(TritValue tv){
        value = tv;
    }

    Trit::Trit(){
        value = Unknown;
    }

    bool Trit::operator==(TritValue tv){
        return value == tv;
    }

    Trit Trit::operator&(Trit ts){
        return Trit(static_cast<TritValue>(value & ts.value));
    }

    Trit Trit::operator|(Trit ts){
        return Trit(static_cast<TritValue>(value | ts.value));
    }

    Trit Trit::operator~(){
        if (value != Unknown) return Trit(static_cast<TritValue>(~value));
        return Trit(value);
    }
}

std::ostream &operator<<(std::ostream &os, trl::Trit &t){
    if (t.value == trl::Unknown) return os << "U";
    if (t.value == trl::False) return os << "F";
    if (t.value == trl::True) return os << "T";
}

std::ostream &operator<<(std::ostream &os, trl::TritValue tv){
    if (tv == trl::Unknown) return os << "U";
    if (tv == trl::False) return os << "F";
    if (tv == trl::True) return os << "T";
}