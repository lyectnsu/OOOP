#include <iostream>

#include "players.h"
#include "game.h"
#include "exceptions.h"
#include "field.h"
#include "cxxopts.hpp"

int main(int argc, char *argv[]) {

    std::cin.sync();

    cxxopts::Options options("BattleShip", "Hope this time my code won't be a plate of spaghetti");
    options.add_options()
            ("h,help", "Get some help about program")
            ("c,count", "Number of rounds", cxxopts::value<int>()->default_value("1"))
            ("f,first", "First player type [Human|Smart|Random]", cxxopts::value<std::string>()->default_value("Human"))
            ("s,second", "Second player type [Human|Smart|Random]", cxxopts::value<std::string>()->default_value("Random"))
            ("g,game", "Game type [Console|Graphic]", cxxopts::value<std::string>()->default_value("Console"));

    auto opts = options.parse(argc, argv);

    if (opts.count("help")) {
        std::cout << options.help() << std::endl;
        exit(0);
    }

    auto nRounds = opts["c"].as<int>();

    auto fPlayerTypeStr = opts["f"].as<std::string>();
    auto sPlayerTypeStr = opts["s"].as<std::string>();
    auto fPlayerType = convertPlayerTypeToEnum(fPlayerTypeStr);
    auto sPlayerType = convertPlayerTypeToEnum(sPlayerTypeStr);

    auto gameTypeStr = opts["g"].as<std::string>();
    auto gameType = convertGameTypeToEnum(gameTypeStr);

    if (nRounds <= 0) {
        throw negative_number_of_rounds_exception();
    }

    if (!playerTypeIsCorrect(fPlayerType)) {
        throw unknown_player_type_exception(fPlayerTypeStr);
    }

    if (!playerTypeIsCorrect(sPlayerType)) {
        throw unknown_player_type_exception(sPlayerTypeStr);
    }

    if (!gameTypeIsCorrect(gameType)) {
        throw unknown_game_type_exception(gameTypeStr);
    }

    if (gameType == G_GRAPHIC) {
        std::cout << "Sorry, but graphic game is not implemented yet! :)\n";
        exit(0);
    }

    game = makeGame(gameType);

    game->clear();
    game->welcome();
    game->standby();


    size_t fPlayerGameScore = 0;
    size_t sPlayerGameScore = 0;

    for (int round = 0; round < nRounds; ++round) {

        if (fPlayerGameScore > nRounds / 2) break;
        if (sPlayerGameScore > nRounds / 2) break;

        game->clear();
        game->say("Round " + std::to_string(round + 1));
        game->standby();

        Player* fPlayer = makePlayer(fPlayerType);
        Player* sPlayer = makePlayer(sPlayerType);

        Field fPlayerField = Field();
        Field fPlayerShipField = Field();
        Field sPlayerField = Field();
        Field sPlayerShipField = Field();

        game->clear();
        game->say("First player ship placing.");
        if (fPlayerType == P_HUMAN) {
            game->standby();
        }

        fPlayer->placeShips(fPlayerShipField);
        game->standby();

        game->clear();

        game->say("Second player ship placing.");
        if (sPlayerType == P_HUMAN) {
            game->standby();
        }

        sPlayer->placeShips(sPlayerShipField);
        game->standby();

        game->clear();

        game->say("Round started.");
        game->standby();

        bool turn = true; // true - fPlayer, false - sPlayer

        while (fPlayer->getScore() < 10 && sPlayer->getScore() < 10) {
            if (turn) {
                if (fPlayerType == P_HUMAN) {
                    game->clear();
                    game->showFields({fPlayerField, fPlayerShipField});
                } else {
                    game->say("Waiting for first player...");
                    game->wait(2);
                }

                auto shootCoords = fPlayer->chooseCell(fPlayerField);
                auto moveState = fPlayer->shoot(fPlayerField, sPlayerShipField, shootCoords);

                if (fPlayerType == P_HUMAN) {
                    if (moveState == MS_MISS) game->missMessage();
                    if (moveState == MS_HIT) game->hitMessage();
                    if (moveState == MS_SUNK) game->sunkMessage();
                    std::getchar(); // костыль
                    game->standby();
                } else {
                    if (moveState == MS_MISS) game->missMessageBad();
                    if (moveState == MS_HIT) game->hitMessageBad();
                    if (moveState == MS_SUNK) game->sunkMessageBad();
                    game->enemyShootMessage(shootCoords);
                    game->wait(2);
                }

                if (moveState == MS_MISS) {
                    turn = !turn;
                    if (fPlayerType == P_HUMAN && sPlayerType == P_HUMAN) {
                        game->clear();
                        game->say("Second player's turn.");
                        game->standby();
                    }
                }
            } else {
                game->clear();
                if (sPlayerType == P_HUMAN) {
                    game->clear();
                    game->showFields({sPlayerField, sPlayerShipField});
                } else {
                    game->say("Waiting for second player...");
                    game->wait(2);
                }

                auto shootCoords = sPlayer->chooseCell(sPlayerField);
                auto moveState = sPlayer->shoot(sPlayerField, fPlayerShipField, shootCoords);

                if (sPlayerType == P_HUMAN) {
                    if (moveState == MS_MISS) game->missMessage();
                    if (moveState == MS_HIT) game->hitMessage();
                    if (moveState == MS_SUNK) game->sunkMessage();
                    std::getchar(); // костыль
                    game->standby();
                } else {
                    if (moveState == MS_MISS) game->missMessageBad();
                    if (moveState == MS_HIT) game->hitMessageBad();
                    if (moveState == MS_SUNK) game->sunkMessageBad();
                    game->enemyShootMessage(shootCoords);
                    game->wait(2);
                }

                if (moveState == MS_MISS) {
                    turn = !turn;
                    if (fPlayerType == P_HUMAN && sPlayerType == P_HUMAN) {
                        game->clear();
                        game->say("First player's turn.");
                    }
                    game->standby();
                }
            }
        }
        game->clear();
        game->say("First player fields:");
        game->showFields({fPlayerField, fPlayerShipField});
        game->say("Second player fields:");
        game->showFields({sPlayerField, sPlayerShipField});
        if (fPlayer->getScore() == 10){
            game->say("First player won a round!");
            fPlayerGameScore += 1;
        }
        else {
            game->say("Second player won a round!");
            sPlayerGameScore += 1;
        }
        game->say("Score: " + std::to_string(fPlayerGameScore) + " - " + std::to_string(sPlayerGameScore));
        game->standby();
    }
    game->clear();
    if (fPlayerGameScore > sPlayerGameScore){
        game->say("First player won the game!");
    }
    if (fPlayerGameScore == sPlayerGameScore){
        game->say("Drawn game!");
    }
    if (fPlayerGameScore < sPlayerGameScore){
        game->say("Second player won the game!");
    }
    return 0;
}
