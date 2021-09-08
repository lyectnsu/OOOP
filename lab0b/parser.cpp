#include "parser.h"

bool check(char symbol){
    return std::isalpha(symbol) || std::isdigit(symbol);
}

std::list<std::string> splitLine(const std::string& inputString){
    int beginOfWord = 0;
    std::list<std::string> result;
    for (int i = 0; i < inputString.size(); ++i){
        while (!check(inputString[i])){
            ++i;
        }
        beginOfWord = i;
        while (check(inputString[i])){
            ++i;
        }
        result.push_back(inputString.substr(beginOfWord, i - beginOfWord));
    }
    return result;
}

std::map<std::string, int> countFrequenciesInFile(const std::string& fileName) {
    std::ifstream file(fileName);

    std::map<std::string, int> freqs;

    std::string buffer;
    while (!file.eof()) {
        std::getline(file, buffer);
        buffer += "#";
        std::list<std::string> words = splitLine(buffer);
        for (auto &word: words) {
            freqs[word] += 1;
        }
    }

    file.close();
    return freqs;
}

void writeCSV(const std::map<std::string, int>& freqs, const std::string& CSVName){
    std::ofstream file(CSVName);

    int totalWords = 0;
    for (const auto& p : freqs){
        totalWords += p.second;
    }

    file << "Word;Count;Percentage\n";
    for (const auto& p : freqs) {
        file << p.first << ";";
        file << p.second << ";";
        file << std::setprecision(1) << std::fixed;
        file << (double) p.second / totalWords * 100 << "\n";
    }

    file.close();
}
