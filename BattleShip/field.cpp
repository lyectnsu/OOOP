#include "field.h"

FieldRow::FieldRow() {
    fldr.resize(10, M_BLANK);
}

std::vector<Mark>::iterator FieldRow::begin() {
    return fldr.begin();
}

std::vector<Mark>::iterator FieldRow::end() {
    return fldr.end();
}

Mark &FieldRow::operator[](size_t index) {
    return fldr[index];
}

Field::Field() {
    fld.resize(10, FieldRow());
}

std::vector<FieldRow>::iterator Field::begin() {
    return fld.begin();
}

std::vector<FieldRow>::iterator Field::end() {
    return fld.end();
}

std::vector<std::pair<size_t, size_t>> Field::getFreeCells() {
    std::vector<std::pair<size_t, size_t>> result;
    for (size_t x = 0; x < 10; ++x){
        for (size_t y = 0; y < 10; ++y){
            if (fld[x][y] == M_BLANK && noShipsAround(x, y)){
                result.emplace_back(x, y);
            }
        }
    }
    return result;
}

std::vector<std::pair<size_t, size_t>> Field::getCellsForShip(size_t shipLength, size_t shipOrientation) {
    std::vector<std::pair<size_t, size_t>> cells;
    for (size_t x = 0; x < 10; ++x){
        for (size_t y = 0; y < 10; ++y){
            if (shipCanFit(x, y, shipLength, shipOrientation)){
                cells.emplace_back(x, y);
            }
        }
    }
    return cells;
}

bool Field::noShipsAround(size_t x, size_t y) {
    bool result = true;
    if (x + 1 < 10 && (fld[x + 1][y] == M_SHIP || fld[x + 1][y] == M_HIT)) result = false;
    if (x - 1 < 10 && (fld[x - 1][y] == M_SHIP || fld[x - 1][y] == M_HIT)) result = false;
    if (y + 1 < 10 && (fld[x][y + 1] == M_SHIP || fld[x][y + 1] == M_HIT)) result = false;
    if (y - 1 < 10 && (fld[x][y - 1] == M_SHIP || fld[x][y - 1] == M_HIT)) result = false;

    if (x + 1 < 10 && y + 1 < 10 && (fld[x + 1][y + 1] == M_SHIP || fld[x + 1][y + 1] == M_HIT)) result = false;
    if (x + 1 < 10 && y - 1 < 10 && (fld[x + 1][y - 1] == M_SHIP || fld[x + 1][y - 1] == M_HIT)) result = false;
    if (x - 1 < 10 && y + 1 < 10 && (fld[x - 1][y + 1] == M_SHIP || fld[x - 1][y + 1] == M_HIT)) result = false;
    if (x - 1 < 10 && y - 1 < 10 && (fld[x - 1][y - 1] == M_SHIP || fld[x - 1][y - 1] == M_HIT)) result = false;

    return result;
}

bool Field::shipCanFit(size_t x, size_t y, size_t shipLength, size_t shipOrientation) {
    bool result = true;
    for (size_t step = 0; step < shipLength; ++step){
        if (shipOrientation == 1){
            if (x + step >= 10 || fld[x + step][y] != M_BLANK || !noShipsAround(x + step, y)){
                result = false;
            }
        }
        if (shipOrientation == 0){
            if (y + step >= 10 || fld[x][y + step] != M_BLANK || !noShipsAround(x, y + step)){
                result = false;
            }
        }
    }
    return result;
}

void Field::placeShip(size_t x, size_t y, size_t shipLength, size_t shipOrientation) {
    for (size_t step = 0; step < shipLength; ++step){
        if (shipOrientation == 1){
            fld[x + step][y] = M_SHIP;
        }
        if (shipOrientation == 0){
            fld[x][y + step] = M_SHIP;
        }
    }
}

void makeBorderRec(Field &field, size_t x, size_t y, size_t px, size_t py){
    if (y >= 10 || x >= 10) return;
    if (field[x][y] == M_MISS) return;
    if (field[x][y] == M_BLANK) {
        field[x][y] = M_MISS;
        return;
    }

    if (x + 1 != px) makeBorderRec(field, x + 1, y, x, y);
    if (x - 1 != px) makeBorderRec(field, x - 1, y, x, y);
    if (y + 1 != py) makeBorderRec(field, x, y + 1, x, y);
    if (y - 1 != py) makeBorderRec(field, x, y - 1, x, y);

    if (x + 1 != px && y + 1 != py) makeBorderRec(field, x + 1, y + 1, x, y);
    if (x + 1 != px && y - 1 != py) makeBorderRec(field, x + 1, y - 1, x, y);
    if (x - 1 != px && y + 1 != py) makeBorderRec(field, x - 1, y + 1, x, y);
    if (x - 1 != px && y - 1 != py) makeBorderRec(field, x - 1, y - 1, x, y);

}

void Field::makeBorder(size_t x, size_t y) {
    makeBorderRec(*this, x, y, 10, 10);
}

size_t shipSizeRec(Field &field, size_t x, size_t y, size_t px, size_t py){
    if (y >= 10 || x >= 10) return 0;
    if (field[x][y] == M_BLANK || field[x][y] == M_MISS) return 0;

    size_t ans = 1;

    if (x + 1 != px) ans += shipSizeRec(field, x + 1, y, x, y);
    if (x - 1 != px) ans += shipSizeRec(field, x - 1, y, x, y);
    if (y + 1 != py) ans += shipSizeRec(field, x, y + 1, x, y);
    if (y - 1 != py) ans += shipSizeRec(field, x, y - 1, x, y);

    return ans;
}

size_t Field::shipSize(size_t x, size_t y){
    return shipSizeRec(*this, x, y, 10, 10);
}

FieldRow &Field::operator[](size_t index) {
    return fld[index];
}

bool shipFullyDown(Field &playerField, Field &enemyShipField, size_t x, size_t y){
    if (playerField.shipSize(x, y) == enemyShipField.shipSize(x, y)){
        return true;
    }
    return false;
}

