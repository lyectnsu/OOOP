#include "../CSVParser.h"
#include "gtest/gtest.h"

#include <string>

template <typename T>
class CSVParserTest : public ::testing::Test{
};

using MyTypes = ::testing::Types<double, int>;
TYPED_TEST_SUITE(CSVParserTest, MyTypes);

TYPED_TEST(CSVParserTest, canConstruct){

    std::string filename = "/home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/test.csv";
    CSVParser<TypeParam> csvp(filename);
}