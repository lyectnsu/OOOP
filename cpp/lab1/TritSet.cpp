#include "TritSet.h"
namespace trl {

    // ################
    // #              #
    // #   TRIT SET   #
    // #              #
    // ################

    TritSet::TritSet(size_t capacity) {
        cellSize = sizeof(uint);
        cellCapacity = 4 * cellSize;
        size_t numOfCells = capacity / cellCapacity;
        // if there are "extra trits" that do not fit in numOfCells cells.
        if (capacity % cellCapacity != 0) {
            ++numOfCells;
        }
        data.resize(numOfCells, 0);
    }

    TritSet::TritSet() {
        cellSize = sizeof(uint);
        cellCapacity = 4 * cellSize;
        data.resize(0, 0);
    }

    size_t TritSet::getCapacity() {
        return data.size() * cellCapacity;
    }

    size_t TritSet::cardinality(TritValue tv) {
        size_t res = 0;
        size_t tsLength = getLength();
        for (int i = 0; i < getLength(); ++i) {
            if ((*this)[i] == tv) {
                ++res;
            }
        }
        return res;
    }

    std::unordered_map<TritValue, size_t> TritSet::cardinality() {
        std::unordered_map<TritValue, size_t> res;
        size_t tsLength = getLength();
        for (int i = 0; i < getLength(); ++i) {
            TritValue tv = (*this)[i];
            res[tv]++;
        }
        return res;
    }

    size_t TritSet::numOfInsignificantCells() {
        size_t insignificant = 0;
        int i = data.size() - 1;
        while (data[i] == 0 && i >= 0) {
            insignificant++;
            --i;
        }
        return insignificant;
    }

    size_t TritSet::numOfInsignificant() {
        size_t insignificant = 0;
        int i = getCapacity() - 1;
        while (i >= 0 && (*this)[i] == Unknown) {
            insignificant++;
            --i;
        }
        return insignificant;
    }

    size_t TritSet::getLength() {
        size_t insignificant = numOfInsignificant();
        size_t capacity = getCapacity();
        return capacity - insignificant;
    }

    void TritSet::shrink() {
        size_t insignificant = numOfInsignificantCells();
        size_t dataSize = data.size();
        data.resize(dataSize - insignificant);
    }

    void TritSet::trim(size_t lastIndex) {
        size_t capacity = getCapacity();
        if (lastIndex >= capacity) {
            return;
        }
        for (int i = lastIndex + 1; i < capacity; ++i) {
            (*this)[i] = Unknown;
        }
    }

    TritPlacer TritSet::operator[](int index) {
        return {this, index};
    }

    TritSet TritSet::operator&(TritSet ts) {
        size_t capacity1 = getCapacity();
        size_t capacity2 = ts.getCapacity();
        size_t maxCapacity = std::max(capacity1, capacity2);
        TritSet res(maxCapacity);
        for (int i = 0; i < maxCapacity; ++i) {
            Trit tr1;
            Trit tr2;
            if (i < capacity1) tr1 = (*this)[i];
            if (i < capacity2) tr2 = ts[i];
            res[i] = tr1 & tr2;
        }
        return res;
    }

    TritSet TritSet::operator|(TritSet ts) {
        size_t capacity1 = getCapacity();
        size_t capacity2 = ts.getCapacity();
        size_t maxCapacity = std::max(capacity1, capacity2);
        TritSet res(maxCapacity);
        for (int i = 0; i < maxCapacity; ++i) {
            Trit tr1;
            Trit tr2;
            if (i < capacity1) tr1 = (*this)[i];
            if (i < capacity2) tr2 = ts[i];
            res[i] = tr1 | tr2;
        }
        return res;
    }

    TritSet TritSet::operator~() {
        size_t capacity = getCapacity();
        TritSet res(capacity);
        for (int i = 0; i < capacity; ++i) {
            Trit tr = (*this)[i];
            tr = ~tr;
            res[i] = tr;
        }
        return res;
    }

    // ###################
    // #                 #
    // #   TRIT PLACER   #
    // #                 #
    // ###################

    TritPlacer::TritPlacer(TritSet *_tsp, int _index) {
        tsp = _tsp;
        index = _index;
    }

    TritPlacer::operator TritValue(){
        size_t cellNum = index / tsp->cellCapacity;
        size_t tritPos = index % tsp->cellCapacity;
        uint cellValue = tsp->data[cellNum];
        uint shift = 2 * (tsp->cellCapacity - tritPos - 1);
        uint mask = 3 << shift;
        uint tvui = (cellValue & mask) >> shift;

        if (tvui == 0) return Unknown;
        if (tvui == 1) return False;
        if (tvui == 3) return True;
    }

    TritPlacer::operator Trit(){
        return {static_cast<TritValue>(*this)};
    }

    void TritPlacer::operator=(TritValue tv){
        this->operator=(Trit(tv));
    }

    void TritPlacer::operator=(Trit t){
        size_t cellNum = index / tsp->cellCapacity;
        size_t tritPos = index % tsp->cellCapacity;
        uint shift = 2 * (tsp->cellCapacity - tritPos - 1);
        if (t == Unknown){
            if (index < tsp->getCapacity()){
                uint mask = ~(3 << shift);
                tsp->data[cellNum] &= mask;
            }
        }
        else {
            if (index >= tsp->getCapacity()){
                size_t newSize = index / tsp->cellCapacity;
                if (index % tsp->cellCapacity != 0) {
                    ++newSize;
                }
                tsp->data.resize(newSize, 0);
            }
            if (t == True){
                uint mask = 3 << shift;
                tsp->data[cellNum] |= mask;
            }
            if (t == False){
                uint mask = ~(3 << shift);
                tsp->data[cellNum] &= mask;
                mask = 1 << shift;
                tsp->data[cellNum] |= mask;
            }
        }
    }

    void TritPlacer::operator=(TritPlacer tp){
        Trit t = tp;
        *this = t;
    }
}

std::ostream &operator<<(std::ostream &os, trl::TritSet &ts){
    size_t tsLength = ts.getLength();
    for (int i = 0; i < tsLength; ++i){
        trl::Trit tr = ts[i];
        os << tr;
    }
    return os;
}

std::ostream &operator<<(std::ostream &os, trl::TritPlacer &tp){
    trl::Trit tr = tp;
    return os << tr;
}