#include "parser.h"

bool check(char symbol){
    return std::isalpha(symbol);
}

bool is_empty(std::ifstream& pFile)
{
    return pFile.peek() == std::ifstream::traits_type::eof();
}

std::list<std::string> splitLine(const std::string& inputString){
    std::string subString;
    std::list<std::string> result;
    int i = 0;
    while (i < inputString.size()){
        if (!check(inputString[i])){
            if (!subString.empty()){
                result.push_back(subString);
            }
        }
        else {
            subString += inputString[i];
        }
        ++i;
    }
    return result;
}

std::vector<std::pair<std::string, int>> countFrequenciesInFile(const std::string& fileName) {
    std::ifstream file(fileName);

    std::map<std::string, int> freqs;
    std::vector<std::pair<std::string, int>> freqsV;
    if (is_empty(file)){
        return freqsV;
    }
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

    for (const auto& p: freqs){
        freqsV.emplace_back(p);
    }

    auto cmp = [](std::pair<std::string, int> const &a, std::pair<std::string, int> const &b){
        return a.second != b.second?  a.second < b.second : a.first < b.first;
    };

    std::sort(freqsV.rbegin(), freqsV.rend(), cmp);

    return freqsV;
}

void writeCSV(std::vector<std::pair<std::string, int>> &freqs, std::string &CSVName){
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
