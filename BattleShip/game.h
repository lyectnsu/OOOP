#pragma once

#include <iostream>

#include "field.h"
#include "effolkroniumRandom.h"

using Random = effolkronium::random_static;

enum GameType {
    G_CONSOLE,
    G_GRAPHIC,
    G_UNKNOWN
};

class Game {
public:
    virtual void welcome() = 0;
    virtual void say(const std::string& message) = 0;
    virtual void standby() = 0;
    virtual void clear() = 0;
    virtual void showFields(std::initializer_list<Field> fieldList) = 0;
    virtual void wait(size_t seconds) = 0;
    virtual void missMessage() = 0;
    virtual void hitMessage() = 0;
    virtual void sunkMessage() = 0;
    virtual void missMessageBad() = 0;
    virtual void hitMessageBad() = 0;
    virtual void sunkMessageBad() = 0;
    virtual void enemyShootMessage(std::pair<size_t, size_t> shootCoords) = 0;
    virtual void input(std::initializer_list<size_t*> pvars) = 0;
};

class ConsoleGame : public Game{
public:
    void welcome() override;
    void say(const std::string& message) override;
    void standby() override;
    void clear() override;
    void showFields(std::initializer_list<Field> fieldList) override;
    void wait(size_t seconds) override;
    void missMessage() override;
    void hitMessage() override;
    void sunkMessage() override;
    void missMessageBad() override;
    void hitMessageBad() override;
    void sunkMessageBad() override;
    void enemyShootMessage(std::pair<size_t, size_t> shootCoords) override;
    void input(std::initializer_list<size_t*> pvars) override;
};

class GraphicGame : public Game{
public:
    void welcome() override;
    void say(const std::string& message) override;
    void standby() override;
    void clear() override;
    void showFields(std::initializer_list<Field> fieldList) override;
    void wait(size_t seconds) override;
    void missMessage() override;
    void hitMessage() override;
    void sunkMessage() override;
    void missMessageBad() override;
    void hitMessageBad() override;
    void sunkMessageBad() override;
    void enemyShootMessage(std::pair<size_t, size_t> shootCoords) override;
    void input(std::initializer_list<size_t*> pvars) override;
};

extern Game* game;

GameType convertGameTypeToEnum(const std::string& gameTypeStr);
bool gameTypeIsCorrect(GameType gameType);
Game* makeGame(GameType gameType);