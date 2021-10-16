#pragma once
#include <string>
#include <list>
#include <map>
#include <fstream>
#include <iomanip>
#include <algorithm>
#include <vector>
#include <cstring>
#include <cstdio>

bool is_empty(FILE* fp);

std::list<std::wstring> splitLine(std::wstring& inputString);

std::vector<std::pair<std::wstring, int>> countFrequenciesInFile(const std::string& fileName);

void writeCSV(std::vector<std::pair<std::wstring, int>> &freqs, std::string& CSVName);

