#include "exceptions.h"

unknown_player_type_exception::unknown_player_type_exception(const std::string &playerTypeStr) {
    msg = R"(Player type only can be "Human", "Smart" or "Random", but ")";
    msg += playerTypeStr;
    msg += R"(" given.)";
}

const char *unknown_player_type_exception::what() const noexcept {
    return msg.c_str();
}

unknown_game_type_exception::unknown_game_type_exception(const std::string &gameTypeStr) {
    msg = R"(Game type only can be "Console" or "Graphic", but ")";
    msg += gameTypeStr;
    msg += R"(" given.)";
}

const char *unknown_game_type_exception::what() const noexcept {
    return msg.c_str();
}

const char *negative_number_of_rounds_exception::what() const noexcept {
    return "Number of rounds can not be a negative number.";
}