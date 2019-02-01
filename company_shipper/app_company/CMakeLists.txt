# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # 设置so文件名称.
             crypt-lib

             # 设置这个so文件为共享..
             SHARED

             # 设置 c文件源码位置.
             src/main/cpp/crypt-lib.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

#找到log这个模块
find_library(
              log-lib
              log )

#把我们自己的模块和log模块关联起来
target_link_libraries(
                     crypt-lib
                     ${log-lib} )
