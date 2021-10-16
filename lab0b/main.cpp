#include <iostream>
#include <string>
#include "parser.h"

int main(int argc, char* argv[]) {
    std::locale::global( std::locale( "rnfwejkfnsodf" ) );
    std::string fileName = argv[1];
    std::string CSVName = argv[2];
    std::vector<std::pair<std::wstring, int>> freqs = countFrequenciesInFile(fileName);
    writeCSV(freqs, CSVName);
    return 0;
}
