#include "players.h"

PlayerType convertPlayerTypeToEnum(const std::string& playerTypeStr){
    if (playerTypeStr == "Human") return P_HUMAN;
    if (playerTypeStr == "Smart") return P_SMART;
    if (playerTypeStr == "Random") return P_RANDOM;
    return P_UNKNOWN;
}

bool playerTypeIsCorrect(const PlayerType& playerType){
    return (playerType != P_UNKNOWN);
}

Player* makePlayer(const PlayerType& playerType){
    if (playerType == P_HUMAN) return new HumanPlayer();
    if (playerType == P_SMART) return new SmartPlayer();
    if (playerType == P_RANDOM) return new RandomPlayer();
}

HumanPlayer::HumanPlayer() {
    score = 0;
}

size_t HumanPlayer::getScore() {
    return score;
}

void HumanPlayer::placeShips(Field &playerField) {
    size_t x, y, shipOrientation;

    for (size_t shipLength = 4; shipLength > 0; --shipLength){
        for (size_t shipAmount = 0; shipAmount < 5 - shipLength; ++shipAmount){
            game->clear();
            game->showFields({playerField});
            game->say("(" + std::to_string(shipAmount + 1) + ")Placing " + std::to_string(shipLength) + "-cell ship.");
            bool correctData = false;
            while (!correctData){
                game->say("Enter coordinates of a ship and its orientation:");
                game->input({&x, &y, &shipOrientation});
                if (x > 9 || y > 9 || shipOrientation > 1){
                    game->say("Incorrect input data!");
                    continue;
                }
                if (!playerField.shipCanFit(x, y, shipLength, shipOrientation)){
                    game->say("The ship cannot be placed here!");
                    continue;
                }
                correctData = true;
            }
            playerField.placeShip(x, y, shipLength, shipOrientation);
        }
    }
}

std::pair<size_t, size_t> HumanPlayer::chooseCell(Field &playerField) {
    size_t x, y;

    bool correctData = false;
    while (!correctData){
        game->say("Enter coordinates of a shoot:");
        game->input({&x, &y});
        if (x > 9 || y > 9){
            game->say("Incorrect coordinates!");
            continue;
        }
        if (playerField[x][y] != M_BLANK){
            game->say("You have already shoot there!");
            continue;
        }
        correctData = true;
    }
    return {x, y};
}

MoveState HumanPlayer::shoot(Field &playerField, Field &enemyShipField, std::pair<size_t, size_t> shootCoords) {
    auto x = shootCoords.first;
    auto y = shootCoords.second;

    bool state = (enemyShipField[x][y] == M_SHIP);

    if (state){
        playerField[x][y] = M_SHIP;
        enemyShipField[x][y] = M_HIT;
        if (shipFullyDown(playerField, enemyShipField, x, y)){
            playerField.makeBorder(x, y);
            enemyShipField.makeBorder(x, y);
            score += 1;
            return MS_SUNK;
        }
        return MS_HIT;
    }
    playerField[x][y] = M_MISS;
    enemyShipField[x][y] = M_MISS;
    return MS_MISS;
}

SmartPlayer::SmartPlayer() {
    score = 0;
    shipsBeaten = new size_t[4];
    foundOrientation = false;
    shootingOrientation = 0;
    shipStart = {10, 10};

    shipsBeaten[0] = 0;
    shipsBeaten[1] = 0;
    shipsBeaten[2] = 0;
    shipsBeaten[3] = 0;
}

SmartPlayer::~SmartPlayer() {
    delete shipsBeaten;
}

size_t SmartPlayer::getScore(){
    return score;
}

std::vector<std::pair<size_t, size_t>> SmartPlayer::getCellsForLongShip(Field &field, size_t shipLength, size_t shipOrientation) {
    std::vector<std::pair<size_t, size_t>> edgeCells;
    if (shipOrientation == 0) {
        for (int y = 0; y < 10 - shipLength; ++y) {
            if (field.shipCanFit(0, y, shipLength, shipOrientation)) {
                edgeCells.emplace_back(0, y);
            }
        }
        for (int y = 0; y < 10 - shipLength; ++y) {
            if (field.shipCanFit(9, y, shipLength, shipOrientation)) {
                edgeCells.emplace_back(9, y);
            }
        }
    }

    if (shipOrientation == 1) {
        for (int x = 1; x < 9 - shipLength; ++x) {
            if (field.shipCanFit(x, 0, shipLength, shipOrientation)) {
                edgeCells.emplace_back(x, 0);
            }
        }
        for (int x = 1; x < 9 - shipLength; ++x) {
            if (field.shipCanFit(x, 9, shipLength, shipOrientation)) {
                edgeCells.emplace_back(x, 9);
            }
        }
    }
    return edgeCells;
}

std::pair<size_t, size_t> SmartPlayer::smartChoosing(Field &field, size_t shipLength) {
    std::vector<std::pair<size_t, size_t>> cells;

    for (size_t x = 0; x < 10; ++x){
        for (size_t y = x % shipLength; y < 10; y += shipLength - 1){
            if (field[x][y] == M_BLANK && field.noShipsAround(x, y)) {
                cells.emplace_back(x, y);
            }
        }
    }

    int cellsSize = cells.size();

    return cells[Random::get(0, cellsSize - 1)];
}

void SmartPlayer::placeShips(Field &playerField) {
    for (size_t shipLength = 4; shipLength > 1; --shipLength){
        for (size_t shipAmount = 0; shipAmount < 5 - shipLength; ++shipAmount){
            size_t shipOrientation = Random::get(0, 1);
            auto edgeCells = getCellsForLongShip(playerField, shipLength, shipOrientation);
            if (edgeCells.empty()){
                shipOrientation = 1 - shipOrientation;
                edgeCells = getCellsForLongShip(playerField, shipLength, shipOrientation);
            }

            int edgeCellsSize = edgeCells.size();
            auto cell = edgeCells[Random::get(0, edgeCellsSize - 1)];

            auto x = cell.first;
            auto y = cell.second;

            playerField.placeShip(x, y, shipLength, shipOrientation);
        }
    }

    for (size_t shipAmount = 0; shipAmount < 4; ++shipAmount){
        auto freeCells = playerField.getFreeCells();

        int freeCellsSize = freeCells.size();
        auto cell = freeCells[Random::get(0, freeCellsSize - 1)];

        auto x = cell.first;
        auto y = cell.second;

        playerField.placeShip(x, y, 1, 1);
    }
}

std::pair<size_t, size_t> SmartPlayer::chooseCell(Field &playerField) {
    if (foundOrientation){
        size_t x = shipStart.first;
        size_t y = shipStart.second;

        if (shootingOrientation == 0){
            while (y < 10 && playerField[x][y] == M_SHIP) {
                ++y;
            }
            if (y == 10 || playerField[x][y] == M_MISS){
                --y;
                while (playerField[x][y] == M_SHIP) {
                    --y;
                }
            }
        }
        if (shootingOrientation == 1){
            while (x < 10 && playerField[x][y] == M_SHIP) {
                ++x;
            }
            if (x == 10 || playerField[x][y] == M_MISS){
                --x;
                while (playerField[x][y] == M_SHIP) {
                    --x;
                }
            }
        }
        return {x, y};
    }
    if (shipStart.first != 10 && shipStart.second != 10){
        size_t x = shipStart.first;
        size_t y = shipStart.second;

        if (x + 1 < 10 && playerField[x + 1][y] == M_BLANK) return {x + 1, y};
        if (x - 1 < 10 && playerField[x - 1][y] == M_BLANK) return {x - 1, y};
        if (y + 1 < 10 && playerField[x][y + 1] == M_BLANK) return {x, y + 1};
        if (y - 1 < 10 && playerField[x][y - 1] == M_BLANK) return {x, y - 1};
    }

    if (shipsBeaten[3] < 1){
        return smartChoosing(playerField, 4);
    }
    if (shipsBeaten[2] != 2){
        return smartChoosing(playerField, 3);
    }
    if (shipsBeaten[1] != 3){
        return smartChoosing(playerField, 2);
    }

    auto freeCells = playerField.getFreeCells();
    int freeCellsSize = freeCells.size();

    return freeCells[Random::get(0, freeCellsSize - 1)];
}

MoveState SmartPlayer::shoot(Field &playerField, Field &enemyShipField, std::pair<size_t, size_t> shootCoords) {
    auto x = shootCoords.first;
    auto y = shootCoords.second;

    bool state = (enemyShipField[x][y] == M_SHIP);

    if (state){
        playerField[x][y] = M_SHIP;
        enemyShipField[x][y] = M_HIT;
        if (shipFullyDown(playerField, enemyShipField, x, y)){
            playerField.makeBorder(x, y);
            enemyShipField.makeBorder(x, y);

            shipsBeaten[playerField.shipSize(x, y) - 1] += 1;
            foundOrientation = false;
            shipStart = {10, 10};

            score += 1;
            return MS_SUNK;
        }
        if (shipStart.first != 10 && shipStart.second != 10){
            if (shipStart.second != y){
                foundOrientation = true;
                shootingOrientation = 0;
            }
            if (shipStart.first != x){
                foundOrientation = true;
                shootingOrientation = 1;
            }
        }
        else {
            shipStart.first = x;
            shipStart.second = y;
        }
        return MS_HIT;
    }
    playerField[x][y] = M_MISS;
    enemyShipField[x][y] = M_MISS;
    return MS_MISS;
}

RandomPlayer::RandomPlayer() {
    score = 0;
}

size_t RandomPlayer::getScore() {
    return score;
}

void RandomPlayer::placeShips(Field &playerField) {
    for (size_t shipLength = 4; shipLength >= 1; --shipLength){
        for (size_t shipAmount = 0; shipAmount < 5 - shipLength; ++shipAmount){
            size_t shipOrientation = Random::get(0, 1);
            auto cells = playerField.getCellsForShip(shipLength, shipOrientation);
            int cellsSize = cells.size();

            auto cell = cells[Random::get(0, cellsSize - 1)];
            auto x = cell.first;
            auto y = cell.second;

            playerField.placeShip(x, y, shipLength, shipOrientation);
        }
    }
}

std::pair<size_t, size_t> RandomPlayer::chooseCell(Field &playerField) {
    auto cells = playerField.getFreeCells();
    int cellsSize = cells.size();
    return cells[Random::get(0, cellsSize - 1)];
}

MoveState RandomPlayer::shoot(Field &playerField, Field &enemyShipField, std::pair<size_t, size_t> shootCoords) {
    auto x = shootCoords.first;
    auto y = shootCoords.second;

    bool state = (enemyShipField[x][y] == M_SHIP);

    if (state){
        playerField[x][y] = M_SHIP;
        enemyShipField[x][y] = M_HIT;
        if (shipFullyDown(playerField, enemyShipField, x, y)){
            playerField.makeBorder(x, y);
            enemyShipField.makeBorder(x, y);
            score += 1;
            return MS_SUNK;
        }
        return MS_HIT;
    }
    playerField[x][y] = M_MISS;
    enemyShipField[x][y] = M_MISS;
    return MS_MISS;
}


