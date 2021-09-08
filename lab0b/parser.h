#pragma once
#include <string>
#include <list>
#include <map>
#include <fstream>
#include <iomanip>

bool check(char symbol);

std::list<std::string> splitLine(const std::string& inputString);

std::map<std::string, int> countFrequenciesInFile(const std::string& fileName);

void writeCSV(const std::map<std::string, int>& freqs, const std::string& CSVName);

