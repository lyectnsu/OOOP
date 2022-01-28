#include "game.h"

Game* game = nullptr;

GameType convertGameTypeToEnum(const std::string& gameTypeStr){
    if (gameTypeStr == "Console") return G_CONSOLE;
    if (gameTypeStr == "Graphic") return G_GRAPHIC;
    return G_UNKNOWN;
}

bool gameTypeIsCorrect(GameType gameType){
    return (gameType != G_UNKNOWN);
}

Game* makeGame(GameType gameType){
    if (gameType == G_CONSOLE) return new ConsoleGame();
    if (gameType == G_GRAPHIC) return new GraphicGame();
}

//   ####################
//   #                  #
//   #   CONSOLE GAME   #
//   #                  #
//   ####################

void ConsoleGame::welcome() {
    std::cout << "\n"
                 "▒█▀▀█ ░█▀▀█ ▀▀█▀▀ ▀▀█▀▀ ▒█░░░ ▒█▀▀▀ ▒█▀▀▀█ ▒█░▒█ ▀█▀ ▒█▀▀█ 　 ▒█▀▀█ ░█▀▀█ ▒█▀▄▀█ ▒█▀▀▀ \n"
                 "▒█▀▀▄ ▒█▄▄█ ░▒█░░ ░▒█░░ ▒█░░░ ▒█▀▀▀ ░▀▀▀▄▄ ▒█▀▀█ ▒█░ ▒█▄▄█ 　 ▒█░▄▄ ▒█▄▄█ ▒█▒█▒█ ▒█▀▀▀ \n"
                 "▒█▄▄█ ▒█░▒█ ░▒█░░ ░▒█░░ ▒█▄▄█ ▒█▄▄▄ ▒█▄▄▄█ ▒█░▒█ ▄█▄ ▒█░░░ 　 ▒█▄▄█ ▒█░▒█ ▒█░░▒█ ▒█▄▄▄ \n";
    std::cout << "   Made by lyect.\n"
                 "   Hope this time my code won't be a plate of spaghetti\n";
}

void ConsoleGame::say(const std::string &message) {
    std::cout << message << "\n";
}

void ConsoleGame::standby() {
    std::cout << "Press ENTER to continue...";
    std::getchar();
}

void ConsoleGame::clear() {
    std::cout << std::flush;
    system("clear");
}

void ConsoleGame::showFields(std::initializer_list<Field> fieldList) {
    for (auto field : fieldList){
        std::cout << "  |";
        for (int i = 0; i < 10; ++i){
            std::cout << i << " ";
        }
        std::cout << "   ";
    }
    std::cout << "\n";

    for (auto field : fieldList){
        std::cout << "--+--------------------   ";
    }
    std::cout << "\n";

    for (int x = 0; x < 10; ++x) {
        for (auto field: fieldList) {
            std::cout << " " << x << "|";
            for (auto cell : field[x]){
                if (cell == M_BLANK) std::cout << "□ ";
                if (cell == M_SHIP) std::cout << "■ ";
                if (cell == M_MISS) std::cout << "▨ ";
                if (cell == M_HIT) std::cout << "✖ ";
            }
            std::cout << "   ";
        }
        std::cout << "\n";
    }
}

void ConsoleGame::wait(size_t seconds) {
    std::string command = "sleep " + std::to_string(seconds) + "s";
    system(command.c_str());
}

void ConsoleGame::missMessage() {
    std::cout << "Captain! We missed!\n";
}

void ConsoleGame::hitMessage() {
    std::cout << "Captain! We hit enemy's ship!\n";
}

void ConsoleGame::sunkMessage() {
    std::cout << "Captain! We sunk enemy's ship!\n";
}

void ConsoleGame::missMessageBad() {
    std::cout << "Few... That was close!\n";
}

void ConsoleGame::hitMessageBad() {
    std::cout << "CAPTAIN! THEY HIT OUR SHIP!\n";
}

void ConsoleGame::sunkMessageBad() {
    std::cout << "CAPTAIN! WE HAVE LOST OUR SHIP!\n";
}

void ConsoleGame::enemyShootMessage(std::pair<size_t, size_t> shootCoords) {
    auto x = shootCoords.first;
    auto y = shootCoords.second;
    auto xStr = std::to_string(x);
    auto yStr = std::to_string(y);
    std::cout << "Enemy shoot at (" << xStr << ", " << yStr << ")\n";
}

void ConsoleGame::input(std::initializer_list<size_t *> pvars) {
    for (auto pvar : pvars){
        std::cin >> *pvar;
    }
}

void GraphicGame::welcome() {}
void GraphicGame::say(const std::string& message) {}
void GraphicGame::standby() {}
void GraphicGame::clear() {}
void GraphicGame::showFields(std::initializer_list<Field> fieldList) {}
void GraphicGame::wait(size_t seconds) {}
void GraphicGame::missMessage() {}
void GraphicGame::hitMessage() {}
void GraphicGame::sunkMessage() {}
void GraphicGame::missMessageBad() {}
void GraphicGame::hitMessageBad() {}
void GraphicGame::sunkMessageBad() {}
void GraphicGame::enemyShootMessage(std::pair<size_t, size_t> shootCoords) {}
void GraphicGame::input(std::initializer_list<size_t*> pvars) {}

