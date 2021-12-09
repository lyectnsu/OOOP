#pragma once

#include <iostream>
#include <utility>
#include <vector>
#include <string>
#include <fstream>
#include <regex>
#include <type_traits>

#include <gtest/gtest.h>

class notEnoughArguments : public std::exception {
private:
    std::string msg;
public:
    notEnoughArguments(const std::string& text, int index, char nls){
        int nlsPrevIndex = 0;
        int linesBefore = 0;
        for (int i = 0; i < index; ++i) {
            if (text[i] == nls) {
                nlsPrevIndex = i;
                linesBefore += 1;
            }
        }

        msg = "ERROR: in line " + std::to_string(linesBefore+1) + ", " + std::to_string(index - nlsPrevIndex) + ":\n";
        for (int i = nlsPrevIndex + 1; i < index; ++i){
            msg += text[i];
        }
        msg += "\n";
        for (int i = nlsPrevIndex + 1; i < index; ++i){
            msg += '~';
        }
        msg += "^\n";
    }
    const char* what() const noexcept{
        return msg.c_str();
    }
};

class tooMuchArguments : public std::exception {
private:
    std::string msg;
public:
    tooMuchArguments(const std::string& text, int index, char nls){
        int nlsPrevIndex = 0;
        int linesBefore = 0;
        for (int i = 0; i < index; ++i) {
            if (text[i] == nls) {
                nlsPrevIndex = i;
                linesBefore += 1;
            }
        }

        msg = "ERROR: in line " + std::to_string(linesBefore+1) + ", " + std::to_string(index - nlsPrevIndex) + ":\n";
        for (int i = nlsPrevIndex + 1; i < index; ++i){
            msg += text[i];
        }
        msg += "\n";
        for (int i = nlsPrevIndex + 1; i < index; ++i){
            msg += '~';
        }
        msg += "^\n";
    }
    const char* what() const noexcept{
        return msg.c_str();
    }
};

class parseIntError : public std::exception {
private:
    std::string msg;
public:
    parseIntError(const std::string& badInt, int i){
        msg = "Can't parse string to integer:\n\t";
        msg += badInt + "\n\t";
        for (int k = 0; k < i; ++k){
            msg += '~';
        }
        msg += "^\n";
    }
    const char* what() const noexcept{
        return msg.c_str();
    }
};

class parseDoubleError : public std::exception {
private:
    std::string msg;
public:
    parseDoubleError(const std::string& badInt, int i){
        msg = "Can't parse string to double:\n\t";
        msg += badInt + "\n\t";
        for (int k = 0; k < i; ++k){
            msg += '~';
        }
        msg += "^\n";
    }
    const char* what() const noexcept{
        return msg.c_str();
    }
};

/*
 * #####################
 * #                   #
 * #   INDEXES TRICK   #
 * #                   #
 * #####################
 */

template<size_t... Indexes>
struct indexes{};

template<size_t N, size_t... Indexes>
struct buildIndexes : buildIndexes<N-1, N-1, Indexes...>{};

template<size_t... Indexes>
struct buildIndexes<0, Indexes...> : indexes<Indexes...>{};

/*
 * ####################
 * #                  #
 * #   PARSE VECTOR   #
 * #                  #
 * ####################
*/

template<typename T> T from_string(std::string &s);

template<> int from_string<int>(std::string &s) {
    for (int i = 0; i < s.size(); ++i){
        if (!std::isdigit(s[i]) && s[i] != ' '){
            throw parseIntError(s, i);
        }
    }
    return std::stoi(s);
}
template<> double from_string<double>(std::string &s) {
    for (int i = 0; i < s.size(); ++i){
        if (!std::isdigit(s[i]) && s[i] != ' ' && s[i] != '.'){
            throw parseDoubleError(s, i);
        }
    }
    return std::stod(s);
}
template<> std::string from_string<std::string>(std::string &s) {return s;};

template<typename... Ts, size_t... Indexes>
std::tuple<Ts...> parseVector(std::vector<std::string> &values, indexes<Indexes...>){
    return {from_string<Ts>(values[Indexes])...};
}

template<typename... Args>
class CSVRow{
public:
    char sep1;
    char sep2;
    char delimiter;
    char newlineSymbol;
    std::tuple<Args...> data;
    CSVRow(char _sep1, char _sep2, char _delimiter, char _newlineSymbol, std::tuple<Args...> _data){
        sep1 = _sep1;
        sep2 = _sep2;
        delimiter = _delimiter;
        newlineSymbol = _newlineSymbol;
        data = _data;
    }
};

template<typename... Args>
class CSVParser{
private:
    std::string filename;
    bool readHeader;
    size_t readSkipCount;
    char sep1;
    char sep2;
    char delimiter;
    char newlineSymbol;
    std::vector<CSVRow<Args...>> data;
public:
    static constexpr const size_t argsSize = (sizeof... (Args));
    std::vector<std::string> header;
    CSVParser(std::string &_filename){
        filename = _filename;

        readHeader = true;
        readSkipCount = 0;
        sep1 = '\"';
        sep2 = '\"';
        delimiter = ';';
        newlineSymbol = '\n';

        readFile();
    }
    CSVParser(std::string &_filename, size_t _readSkipCount){
        filename = _filename;
        readHeader = true;
        readSkipCount = _readSkipCount;
        sep1 = '\"';
        sep2 = '\"';
        delimiter = ';';
        newlineSymbol = '\n';

        readFile();
    }
    CSVParser(std::string &_filename, size_t _readSkipCount, bool _readHeader){
        filename = _filename;
        readHeader = _readHeader;
        readSkipCount = _readSkipCount;
        sep1 = '\"';
        sep2 = '\"';
        delimiter = ';';
        newlineSymbol = '\n';

        readFile();
    }
    CSVParser(std::string &_filename, size_t _readSkipCount, std::string &_separator, char _delimiter, char _newlineSymbol){
        filename = _filename;
        readHeader = true;
        readSkipCount = _readSkipCount;
        sep1 = _separator[0];
        sep2 = _separator[1];
        delimiter = _delimiter;
        newlineSymbol = _newlineSymbol;

        readFile();
    }

    CSVParser(std::string &_filename, size_t _readSkipCount, bool _readHeader, std::string &_separator, char _delimiter, char _newlineSymbol){
        filename = _filename;
        readHeader = _readHeader;
        readSkipCount = _readSkipCount;
        sep1 = _separator[0];
        sep2 = _separator[1];
        delimiter = _delimiter;
        newlineSymbol = _newlineSymbol;

        readFile();
    }

    void readFile() {
        std::ifstream ifs(filename);
        std::string wholeText;
        std::string readBuffer;

        while (!ifs.eof()) {
            std::getline(ifs, readBuffer);
            readBuffer += "\n";
            wholeText += readBuffer;
        }

        std::vector<std::string> wordsBuffer;
        std::string wordBuffer;
        int wordsRead = 0;
        int skipped = 0;
        bool headerReadFlag = false;
        if (readHeader) headerReadFlag = true;

        for (int i = 0; i < wholeText.size(); ++i) {
            if (wholeText[i] == delimiter || wholeText[i] == newlineSymbol) {
                if (!wordBuffer.empty()) {
                    wordsBuffer.push_back(wordBuffer);
                    wordBuffer.clear();
                    wordsRead += 1;
                }
                if (wholeText[i] == newlineSymbol) {
                    if (headerReadFlag){
                        header = wordsBuffer;
                        headerReadFlag = false;
                    }
                    else if (skipped < readSkipCount){
                        skipped += 1;
                    }
                    else if (wordsRead < argsSize) {
                        throw notEnoughArguments(wholeText, i, newlineSymbol);
                    }
                    else {
                        if (wordsBuffer.size() > argsSize){
                            throw tooMuchArguments(wholeText, i, newlineSymbol);
                        }
                        std::tuple<Args...> tupleToPush = parseVector<Args...>(wordsBuffer, buildIndexes<argsSize>());
                        CSVRow<Args...> rowToPush = CSVRow<Args...>(sep1, sep2, delimiter, newlineSymbol, tupleToPush);
                        data.push_back(rowToPush);
                    }
                    wordsBuffer.clear();
                    wordBuffer.clear();
                    wordsRead = 0;
                }
            }
            else {
                wordBuffer += wholeText[i];
            }
        }
    }

    typename std::vector<CSVRow<Args...>>::iterator begin(){
        return data.begin();
    }

    typename std::vector<CSVRow<Args...>>::iterator end(){
        return data.end();
    }

};

/*
 * ###################
 * #                 #
 * #   PRINT TUPLE   #
 * #                 #
 * ###################
*/

template<class Ch, class Tr, class Tuple, size_t... Indexes>
static void subprint_tuple(std::basic_ostream<Ch, Tr>& os, char sep1, char sep2, char delim, char newlineSymbol, Tuple const& t, indexes<Indexes...>){
    os << sep1;
    int dummy[] = {0, ( (void) (os << ((Indexes == 0 ? "" : std::string(1, delim))) << std::get<Indexes>(t)), 0) ...};
    os << sep2 << newlineSymbol;
}

template<class Ch, class Tr, typename... Args>
std::basic_ostream<Ch, Tr>& operator<<(std::basic_ostream<Ch, Tr>& os, CSVRow<Args...>& csvr){
    subprint_tuple(os, csvr.sep1, csvr.sep2, csvr.delimiter, csvr.newlineSymbol, csvr.data, buildIndexes<sizeof... (Args)>());
    return os;
}

template<class Ch, class Tr, typename... Args>
std::basic_ostream<Ch, Tr>& operator<<(std::basic_ostream<Ch, Tr>& os, CSVParser<Args...>& csvp){
    for (int i = 0; i < csvp.argsSize; ++i){
        if (i >= csvp.header.size()){
            os << "NULL";
        }
        else {
            os << csvp.header[i];
        }
        if (i != csvp.argsSize - 1) {
            os << "|";
        }
    }
    os << "\n";
    for (auto x : csvp){
        os << x;
    }
    return os;
}
