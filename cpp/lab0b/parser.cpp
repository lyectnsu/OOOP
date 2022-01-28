#include "parser.h"

bool is_empty(std::wifstream& pFile){
    return pFile.peek() == std::wifstream::traits_type::eof();
}

std::list<std::wstring> splitLine(std::wstring& inputString){
    std::wstring subString;
    std::list<std::wstring> result;

    size_t i = 0;

    while (i < inputString.size()){
       if (!iswalpha(inputString[i])) {
            if (!subString.empty()) {
                result.push_back(subString);
                subString.clear();
            }
        } else {
            subString += inputString[i];
        }
        ++i;
    }
    return result;
}

std::vector<std::pair<std::wstring, int>> countFrequenciesInFile(const std::string& fileName) {
    std::wifstream file(fileName);

    std::map<std::wstring, int> freqs;
    std::vector<std::pair<std::wstring, int>> freqsV;
    if (is_empty(file)){
        return freqsV;
    }

    std::wstring buffer;
    while (!file.eof()) {
        std::getline(file, buffer);
        buffer += L'#';
        std::list<std::wstring> words = splitLine(buffer);
        for (auto &word: words) {
            freqs[word] += 1;
        }
    }

    for (const auto& p: freqs){
        freqsV.emplace_back(p);
    }

    auto cmp = [](std::pair<std::wstring, int> const &a, std::pair<std::wstring, int> const &b){
        return a.second != b.second?  a.second < b.second : a.first < b.first;
    };

    std::sort(freqsV.rbegin(), freqsV.rend(), cmp);

    return freqsV;
}

void writeCSV(std::vector<std::pair<std::wstring, int>> &freqs, std::string &CSVName){
    std::wofstream file(CSVName);

    int totalWords = 0;
    for (const auto& p : freqs){
        totalWords += p.second;
    }

    file << L"Word;Count;Percentage\n";
    for (const auto& p : freqs) {
        file << p.first << L";";
        file << p.second << L";";
        file << std::setprecision(1) << std::fixed;
        file << (double) p.second / totalWords * 100 << L"\n";
    }

    file.close();
}
