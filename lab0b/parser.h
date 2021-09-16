#pragma once
#include <string>
#include <list>
#include <map>
#include <fstream>
#include <iomanip>
#include <algorithm>
#include <vector>

bool check(char symbol);

bool is_empty(std::ifstream& pFile);

std::list<std::string> splitLine(const std::string& inputString);

std::vector<std::pair<std::string, int>> countFrequenciesInFile(const std::string& fileName);

void writeCSV(std::vector<std::pair<std::string, int>> &freqs, std::string& CSVName);

