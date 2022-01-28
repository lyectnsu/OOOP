#pragma once

#include <string>

#include "field.h"
#include "game.h"

enum PlayerType {
    P_HUMAN,
    P_SMART,
    P_RANDOM,
    P_UNKNOWN
};

enum MoveState {
    MS_MISS,
    MS_HIT,
    MS_SUNK
};

class Player {
public:
    virtual size_t getScore() = 0;
    virtual void placeShips(Field &playerField) = 0;
    virtual std::pair<size_t, size_t> chooseCell(Field &playerField) = 0;
    virtual MoveState shoot(Field &playerField, Field &enemyShipField, std::pair<size_t, size_t> shootCoords) = 0;
};

class HumanPlayer : public Player {
private:
    size_t score;

public:
    HumanPlayer();

    size_t getScore() override;
    void placeShips(Field &playerField) override;
    std::pair<size_t, size_t> chooseCell(Field &playerField) override;
    MoveState shoot(Field &playerField, Field &enemyShipField, std::pair<size_t, size_t> shootCoords) override;
};

class SmartPlayer : public Player {
private:
    size_t score;
    size_t *shipsBeaten;
    bool foundOrientation;
    size_t shootingOrientation;
    std::pair<size_t, size_t> shipStart;

public:
    SmartPlayer();
    ~SmartPlayer();
    static std::vector<std::pair<size_t, size_t>> getCellsForLongShip(Field &field, size_t shipLength, size_t shipOrientation);
    static std::pair<size_t, size_t> smartChoosing(Field &field, size_t shipLength);

    size_t getScore() override;
    void placeShips(Field &playerField) override;
    std::pair<size_t, size_t> chooseCell(Field &playerField) override;
    MoveState shoot(Field &playerField, Field &enemyShipField, std::pair<size_t, size_t> shootCoords) override;
};

class RandomPlayer : public Player {
private:
    size_t score;
public:
    RandomPlayer();

    size_t getScore() override;
    void placeShips(Field &playerField) override;
    std::pair<size_t, size_t> chooseCell(Field &playerField) override;
    MoveState shoot(Field &playerField, Field &enemyShipField, std::pair<size_t, size_t> shootCoords) override;
};

PlayerType convertPlayerTypeToEnum(const std::string &playerTypeStr);
bool playerTypeIsCorrect(const PlayerType &playerType);
Player *makePlayer(const PlayerType &playerType);
