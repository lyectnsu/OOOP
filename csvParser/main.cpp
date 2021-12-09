#include <iostream>
#include <tuple>

#include "CSVParser.h"

int main() {
    std::string filename = "test.csv";
    std::string separators = "  ";
    char delimiter = ';';
    char newlineSymbol = '\n';
    //CSVParser<int> csvp(filename, 0, true, separators, delimiter, newlineSymbol);

    //std::cout << csvp;

    return 0;
}
