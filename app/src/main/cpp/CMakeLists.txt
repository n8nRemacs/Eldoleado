cmake_minimum_required(VERSION 3.22.1)
project("eldoleado")

# Add include path for node headers
include_directories(${CMAKE_SOURCE_DIR}/../../../libnode/include/node/)

# Add the native library
add_library(native-lib SHARED native-lib.cpp)

# Import libnode as a shared library
add_library(libnode SHARED IMPORTED)
set_target_properties(libnode PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/../../../libnode/bin/${ANDROID_ABI}/libnode.so)

# Link libraries
find_library(log-lib log)
target_link_libraries(native-lib ${log-lib} libnode)
