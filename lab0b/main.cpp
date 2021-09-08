#include <iostream>
#include <string>
#include <map>
#include "parser.h"

int main(int argc, char* argv[]) {
    std::string fileName = argv[1];
    std::string CSVName = argv[2];
    std::map<std::string, int> freqs = countFrequenciesInFile(fileName);
    writeCSV(freqs, CSVName);
    return 0;
}
