cmake_minimum_required(VERSION 3.20)
project(csvParser)

set(CMAKE_CXX_STANDARD 14)

add_executable(csvParser main.cpp CSVParser.h)
add_subdirectory(GoogleTests)