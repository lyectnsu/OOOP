#include <iostream>

#include "workers.h"
#include "factories.h"
#include "parser.h"


int main() {
    std::string filename = "test.txt";
    std::vector<std::string> text;
    Parser p(filename);
    p.parse();
    auto b = p.getBlocks();
    auto s = p.getSequence();
    for (auto x : s){
        b[x]->execute(text);
    }
    return 0;
}
