cmake_minimum_required(VERSION 3.18.1)
project("lsp_api")

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_library(lsp_api SHARED lsp_main.cpp elf_util.cpp)
find_package(dobby REQUIRED CONFIG)
find_package(lsplant REQUIRED CONFIG)
target_link_libraries(lsp_api log dobby::dobby lsplant::lsplant)
