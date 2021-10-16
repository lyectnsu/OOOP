#include "parser.h"

Parser::Parser(std::string const &filename){
    fin.open(filename);
    WrdFactory = new WorkerReadFactory;
    WwtFactory = new WorkerWriteFactory;
    WgpFactory = new WorkerGrepFactory;
    WstFactory = new WorkerSortFactory;
    WrcFactory = new WorkerReplaceFactory;
    WdpFactory = new WorkerDumpFactory;
}
std::vector<std::string> Parser::parseBlock(std::string const &currentString){
    int i = 0;
    std::string blockNumberStr;
    std::string blockTypeStr;
    std::string blockArgsStr;
    if (currentString[i] == ' ') throw BlockSyntaxError(currentString, i);
    while (0 <= currentString[i]-'0' && currentString[i]-'0' <= 9 && i < currentString.size()){
        blockNumberStr += currentString[i];
        ++i;
    }
    if (i >= currentString.size()) throw BlockSyntaxError(currentString, i);

    // left space from equals sign
    if (currentString[i] != ' ') throw BlockSyntaxError(currentString, i);
    i++;
    if (i >= currentString.size()) throw BlockSyntaxError(currentString, i);

    // equals sign
    if (currentString[i] != '=') throw BlockSyntaxError(currentString, i);
    i++;
    if (i >= currentString.size()) throw BlockSyntaxError(currentString, i);

    // right space from equals sign
    if (currentString[i] != ' ') throw BlockSyntaxError(currentString, i);
    i++;
    if (i >= currentString.size()) throw BlockSyntaxError(currentString, i);

    while (currentString[i] != ' ' && i < currentString.size()) {
        blockTypeStr += currentString[i];
        ++i;
    }
    blockArgsStr = currentString.substr(i, currentString.size() - i);
    return {blockNumberStr, blockTypeStr, blockArgsStr};
}

std::vector<int> Parser::parseCommandSequence(std::string const &currentString){
    std::vector<int> resultSequence;
    int lastOut = 0;
    for (int i = 0; i < currentString.size(); ++i){
        if (!std::isdigit(currentString[i])) throw SequenceSyntaxError(currentString, i);
        int blockNumber = 0;
        while (std::isdigit(currentString[i]) && i < currentString.size()){
            blockNumber *= 10;
            blockNumber += currentString[i] - '0';
            i++;
        }
        if (blocks[blockNumber]->argsIn != lastOut) throw DiscrepArgsException();
        resultSequence.push_back(blockNumber);
        lastOut = blocks[blockNumber]->argsOut;
        if (i == currentString.size()) return resultSequence;
        if (currentString[i] != '-') throw SequenceSyntaxError(currentString, i);
        i++;
        if (currentString[i] != '>' || i >= currentString.size()) throw SequenceSyntaxError(currentString, i);
    }
    return resultSequence;
}

void Parser::parse(){
    ParserState state = DEFUNK;
    while(!fin.eof()) {
        std::getline(fin, buffer);
        if (state == DEFUNK) {
            if (buffer.substr(0, 4) == "desc"){
                state = DEFOPEN;
                continue;
            }
            else
                throw FileBeginningException();
        }
        if (state == DEFOPEN){
            if (buffer == "csed")
                state = DEFCLOSE;
            else {
                auto blockStr = parseBlock(buffer);
                int blockNumber = std::stoi(blockStr[0]);
                if (blocks.find(blockNumber) != blocks.end()){
                    throw BadIndexException(blockStr[0], blockStr[1]);
                }
                if (blockStr[1] == "readfile")
                    blocks[blockNumber] = makeWorker(WrdFactory, blockStr[2]);
                if (blockStr[1] == "writefile")
                    blocks[blockNumber] = makeWorker(WwtFactory, blockStr[2]);
                if (blockStr[1] == "grep")
                    blocks[blockNumber] = makeWorker(WgpFactory, blockStr[2]);
                if (blockStr[1] == "sort")
                    blocks[blockNumber] = makeWorker(WstFactory, blockStr[2]);
                if (blockStr[1] == "replace")
                    blocks[blockNumber] = makeWorker(WrcFactory, blockStr[2]);
                if (blockStr[1] == "dump")
                    blocks[blockNumber] = makeWorker(WdpFactory, blockStr[2]);
                continue;
            }
        }
        else if (state == DEFCLOSE) {
            commandSequence = parseCommandSequence(buffer);
            state = DEFEND;
        }
    }
    fin.close();
    if (state != DEFEND){
        throw NoSequenceException();
    }
}

std::vector<int> Parser::getSequence(){
    return commandSequence;
}
std::map<int, Worker*> Parser::getBlocks(){
    return blocks;
}