# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.21

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /snap/clion/175/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /snap/clion/175/bin/cmake/linux/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/lyect/Desktop/OOOP/csvParser

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug

# Include any dependencies generated for this target.
include GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/depend.make
# Include the progress variables for this target.
include GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/progress.make

# Include the compile flags for this target's objects.
include GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/flags.make

GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/src/gmock-all.cc.o: GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/flags.make
GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/src/gmock-all.cc.o: ../GoogleTests/lib/googlemock/src/gmock-all.cc
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/src/gmock-all.cc.o"
	cd /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/GoogleTests/lib/googlemock && /usr/bin/g++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/gmock.dir/src/gmock-all.cc.o -c /home/lyect/Desktop/OOOP/csvParser/GoogleTests/lib/googlemock/src/gmock-all.cc

GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/src/gmock-all.cc.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/gmock.dir/src/gmock-all.cc.i"
	cd /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/GoogleTests/lib/googlemock && /usr/bin/g++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/lyect/Desktop/OOOP/csvParser/GoogleTests/lib/googlemock/src/gmock-all.cc > CMakeFiles/gmock.dir/src/gmock-all.cc.i

GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/src/gmock-all.cc.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/gmock.dir/src/gmock-all.cc.s"
	cd /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/GoogleTests/lib/googlemock && /usr/bin/g++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/lyect/Desktop/OOOP/csvParser/GoogleTests/lib/googlemock/src/gmock-all.cc -o CMakeFiles/gmock.dir/src/gmock-all.cc.s

# Object files for target gmock
gmock_OBJECTS = \
"CMakeFiles/gmock.dir/src/gmock-all.cc.o"

# External object files for target gmock
gmock_EXTERNAL_OBJECTS =

lib/libgmockd.a: GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/src/gmock-all.cc.o
lib/libgmockd.a: GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/build.make
lib/libgmockd.a: GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX static library ../../../lib/libgmockd.a"
	cd /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/GoogleTests/lib/googlemock && $(CMAKE_COMMAND) -P CMakeFiles/gmock.dir/cmake_clean_target.cmake
	cd /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/GoogleTests/lib/googlemock && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/gmock.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/build: lib/libgmockd.a
.PHONY : GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/build

GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/clean:
	cd /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/GoogleTests/lib/googlemock && $(CMAKE_COMMAND) -P CMakeFiles/gmock.dir/cmake_clean.cmake
.PHONY : GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/clean

GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/depend:
	cd /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/lyect/Desktop/OOOP/csvParser /home/lyect/Desktop/OOOP/csvParser/GoogleTests/lib/googlemock /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/GoogleTests/lib/googlemock /home/lyect/Desktop/OOOP/csvParser/cmake-build-debug/GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : GoogleTests/lib/googlemock/CMakeFiles/gmock.dir/depend

