cmake_minimum_required(VERSION 3.20)
project(BattleShip)

set(CMAKE_CXX_STANDARD 14)

add_executable(BattleShip main.cpp cxxopts.hpp exceptions.cpp exceptions.h players.cpp players.h game.cpp game.h field.cpp field.h effolkroniumRandom.h)
