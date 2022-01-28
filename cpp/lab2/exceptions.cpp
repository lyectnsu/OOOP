#include "exceptions.h"

const char * FileBeginningException::what() const noexcept{
    return "ERROR : File must to begin with \"desc\" line!";
}

const char * NoSequenceException::what() const noexcept {
    return "ERROR : No command sequence was given!\n";
}

BlockSyntaxError::BlockSyntaxError(const std::string &str, int pos){
    msg = "ERROR : Block syntax error!\n" + str;
    msg += '\n';
    msg.append(pos, '~');
    msg += '^';
}
const char * BlockSyntaxError::what() const noexcept{
    return msg.c_str();
}

SequenceSyntaxError::SequenceSyntaxError(std::string const &str, int pos){
    msg = "ERROR : Sequence syntax error!\n" + str;
    msg += '\n';
    msg.append(pos, '~');
    msg += '^';
}
const char * SequenceSyntaxError::what() const noexcept{
    return msg.c_str();
}

TooMuchArgsException::TooMuchArgsException(const std::string &str) {
    msg = "ERROR : Too much arguments for worker \"" + str + "\"!\n";
}

const char *TooMuchArgsException::what() const noexcept {
    return msg.c_str();
}

TooFewArgsException::TooFewArgsException(const std::string &str) {
    msg = "ERROR : Too few arguments for worker \"" + str + "\"!\n";
}

const char *TooFewArgsException::what() const noexcept {
    return msg.c_str();
}

const char *DiscrepArgsException::what() const noexcept {
    return "ERROR : Discrepancy in the number of arguments!\n";
}

BadIndexException::BadIndexException(const std::string &str1, const std::string &str2) {
    msg = "ERROR : Can not index command \"" + str2 + "\". ";
    msg += "The number \"" + str1 + "\" is used somewhere before!\n";
}

const char *BadIndexException::what() const noexcept {
    return msg.c_str();
}