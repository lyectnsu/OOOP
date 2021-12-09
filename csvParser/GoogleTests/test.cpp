#include "../CSVParser.h"
#include "gtest/gtest.h"

#include <string>

template <typename T>
class CSVParserTest : public ::testing::Test{
public:
    CSVParser<T>* csvp;
protected:
    virtual void TearDown() {
        std::cout << "222";
    }

    virtual void SetUp() {
        std::string filename = "../cmake-build-debug/test.csv";
        csvp = new CSVParser<T>(filename);
    }

};

using MyTypes = ::testing::Types<double, int>;
TYPED_TEST_SUITE(CSVParserTest, MyTypes);

TYPED_TEST(CSVParserTest, canConstruct){

    std::cout << *(this->csvp);
    std::cout << "111";
}