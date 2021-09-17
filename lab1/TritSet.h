#pragma once

#include <unordered_map>
#include <iostream>
#include <vector>

#include "Trit.h"

namespace trl {

    class TritPlacer;
    class TritSet;

    class TritSet {
    private:
        friend class TritPlacer;
        std::vector<uint> data;
        size_t cellSize;
        size_t cellCapacity;
    public:
        TritSet(size_t capacity);
        TritSet();

        size_t getCapacity();

        size_t numOfInsignificantCells();
        size_t numOfInsignificant();

        size_t getLength();

        void shrink();

        void trim(size_t lastIndex);

        size_t cardinality(TritValue tv);
        std::unordered_map<TritValue, size_t> cardinality();

        TritPlacer operator[](int index);
        TritSet operator&(TritSet ts);
        TritSet operator|(TritSet ts);
        TritSet operator~();
    };

    class TritPlacer{
    private:
        TritSet* tsp;
        int index;
    public:
        TritPlacer(TritSet* _tsp, int _index);
        operator TritValue();
        operator Trit();

        void operator=(TritValue tv);
        void operator=(Trit tv);
    };
}

std::ostream &operator<<(std::ostream &os, trl::TritSet &ts);

std::ostream &operator<<(std::ostream &os, trl::TritPlacer &tp);