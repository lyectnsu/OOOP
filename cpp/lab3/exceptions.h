#pragma once

#include <stdexcept>

class unknown_player_type_exception : public std::exception{
private:
    std::string msg;
public:
    unknown_player_type_exception(const std::string &playerTypeStr);
    const char * what() const noexcept override;
};

class unknown_game_type_exception : public std::exception{
private:
    std::string msg;
public:
    unknown_game_type_exception(const std::string &gameTypeStr);
    const char * what() const noexcept override;
};

class negative_number_of_rounds_exception : public std::exception{
public:
    const char * what() const noexcept override;
};