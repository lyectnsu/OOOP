#pragma once

#include <algorithm>
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <regex>
#include "exceptions.h"

class Worker{
public:
    int argsIn;
    int argsOut;
    virtual void execute(std::vector<std::string> &text) = 0;
};

class WorkerRead : public Worker{
public:
    std::string filename;
    WorkerRead(std::string const &args);
    void execute(std::vector<std::string> &text) override;
};
class WorkerWrite : public Worker{
public:
    std::string filename;
    WorkerWrite(std::string const &args);
    void execute(std::vector<std::string> &text) override;
};
class WorkerGrep : public Worker{
public:
    std::string word;
    WorkerGrep(std::string const &args);
    void execute(std::vector<std::string> &text) override;
};
class WorkerSort : public Worker{
public:
    WorkerSort(std::string const &args);
    void execute(std::vector<std::string> &text) override;
};
class WorkerReplace : public Worker{
public:
    std::string word1, word2;
    WorkerReplace(std::string const &args);
    void execute(std::vector<std::string> &text) override;
};
class WorkerDump : public Worker{
public:
    std::string filename;
    WorkerDump(std::string const &args);
    void execute(std::vector<std::string> &text) override;
};