cmake_minimum_required(VERSION 3.20)
project(lab2)

set(CMAKE_CXX_STANDARD 14)

add_executable(lab2 main.cpp workers.cpp workers.h factories.cpp factories.h parser.cpp parser.h exceptions.cpp exceptions.h)
