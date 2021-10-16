#include "factories.h"
Worker *WorkerReadFactory::createWorker(std::string args) {
    return new WorkerRead(args);
}

Worker *WorkerWriteFactory::createWorker(std::string args){
    return new WorkerWrite(args);
}

Worker *WorkerGrepFactory::createWorker(std::string args){
    return new WorkerGrep(args);
}

Worker *WorkerSortFactory::createWorker(std::string args){
    return new WorkerSort(args);
}

Worker *WorkerReplaceFactory::createWorker(std::string args){
    return new WorkerReplace(args);
}

Worker *WorkerDumpFactory::createWorker(std::string args){
    return new WorkerDump(args);
}

Worker* makeWorker(WorkerFactory* factory, std::string &args){
    return factory->createWorker(args);
}