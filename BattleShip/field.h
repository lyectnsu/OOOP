#pragma once

#include <vector>

enum Mark{
    M_BLANK,
    M_SHIP,
    M_MISS,
    M_HIT
};

class FieldRow {
private:
    std::vector<Mark> fldr;
public:

    FieldRow();

    std::vector<Mark>::iterator begin();
    std::vector<Mark>::iterator end();

    Mark& operator[](size_t index);
};

class Field{
private:
    std::vector<FieldRow> fld;
public:
    Field();

    std::vector<FieldRow>::iterator begin();
    std::vector<FieldRow>::iterator end();

    std::vector<std::pair<size_t, size_t>> getFreeCells();
    std::vector<std::pair<size_t, size_t>> getCellsForShip(size_t shipLength, size_t shipOrientation);
    bool noShipsAround(size_t x, size_t y);
    bool shipCanFit(size_t x, size_t y, size_t shipLength, size_t shipOrientation);
    void placeShip(size_t x, size_t y, size_t shipLength, size_t shipOrientation);
    void makeBorder(size_t x, size_t y);
    size_t shipSize(size_t x, size_t y);
    FieldRow& operator[](size_t index);
};

bool shipFullyDown(Field &playerField, Field &enemyShipField, size_t x, size_t y);
