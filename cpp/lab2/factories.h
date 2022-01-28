#pragma once

#include <iostream>
#include "workers.h"

class WorkerFactory {
public:
    virtual Worker *createWorker(std::string args) = 0;
};

class WorkerReadFactory : public WorkerFactory{
public:
    Worker *createWorker(std::string args) override;
};

class WorkerWriteFactory : public WorkerFactory{
public:
    Worker *createWorker(std::string args) override;
};

class WorkerGrepFactory : public WorkerFactory{
public:
    Worker *createWorker(std::string args) override;
};

class WorkerSortFactory : public WorkerFactory{
public:
    Worker *createWorker(std::string args) override;
};

class WorkerReplaceFactory : public WorkerFactory{
public:
    Worker *createWorker(std::string args) override;
};

class WorkerDumpFactory : public WorkerFactory{
public:
    Worker *createWorker(std::string args) override;
};

Worker* makeWorker(WorkerFactory* factory, std::string &args);