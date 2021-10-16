#pragma once

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <map>

#include "workers.h"
#include "factories.h"
#include "exceptions.h"

enum ParserState{
    DEFUNK,
    DEFOPEN,
    DEFCLOSE,
    DEFEND
};

class Parser{
private:
    std::ifstream fin;
    std::string buffer;
    std::vector<int> commandSequence;
    std::map<int, Worker*> blocks;
    WorkerReadFactory*    WrdFactory;
    WorkerWriteFactory*   WwtFactory;
    WorkerGrepFactory*    WgpFactory;
    WorkerSortFactory*    WstFactory;
    WorkerReplaceFactory* WrcFactory;
    WorkerDumpFactory*    WdpFactory;

    std::vector<std::string> parseBlock(std::string const &currentString);
    std::vector<int> parseCommandSequence(std::string const &currentString);

public:

    Parser(std::string const &filename);
    void parse();
    std::vector<int> getSequence();
    std::map<int, Worker*> getBlocks();
};
