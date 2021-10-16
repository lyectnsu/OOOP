#pragma once

#include <stdexcept>

class FileBeginningException : public std::exception{
public:
    const char * what() const noexcept override;
};

class NoSequenceException : public std::exception{
public:
    const char * what() const noexcept override;
};

class BlockSyntaxError : public std::exception{
private:
    std::string msg;
public:
    BlockSyntaxError(std::string const &str, int pos);
    const char * what() const noexcept override;
};

class SequenceSyntaxError : public std::exception{
private:
    std::string msg;
public:
    SequenceSyntaxError(const std::string &str, int pos);
    const char * what() const noexcept override;
};

class TooMuchArgsException : public std::exception{
private:
    std::string msg;
public:
    TooMuchArgsException(const std::string &str);
    const char * what() const noexcept override;
};

class TooFewArgsException : public std::exception{
private:
    std::string msg;
public:
    TooFewArgsException(const std::string &str);
    const char * what() const noexcept override;
};

class DiscrepArgsException : public std::exception{
public:
    const char * what() const noexcept override;
};

class BadIndexException : public std::exception{
private:
    std::string msg;
public:
    BadIndexException(const std::string &str1, const std::string &str2);
    const char * what() const noexcept override;
};