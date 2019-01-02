# codemon
This repository contains an application for use in a Core Wars type program we called Codemon. This application was designed for creating, editing, and submitting codemon programs for battle with other codemon in a single memory space. This program comes with a compiler (lex/yacc) for compiling code into binary files, and a GUI for edting codemon files, viewing battle outcomes, and uploading codemon to the server. The background logic for compiling codemon programs into binary files is written in C, the GUI was programmed in Java, and the two were linked together using the Java Native Interface.

# Authors
Braydon Johnson

# Building the Project
To build the program simple navigate to the project folder with this `README` in it and type `make`.
**Note** this program requires the java native interface libraries to be included during compilation, this will require you to edit the make file and replace the lines below with the path specific to your computer:
```
-I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include 
-I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include/darwin
```
Run the command:
```
make clean
```
to remove class files and executables from the project folder

# Running
After building the project with `make`, type `make run` to execute the program.
**Note** that the servers that were used for this project are no longer running, attempting to submit a codemon to the server will not work and could even crash the program. Use this project at your leisure but know that many of its aspects may no longer work properly.

# Testing
You will find a 12 programs in the `Source` folder of this project. These programs were used to test the compiler to ensure it works, and once compiled were submitted to the server to ensure they were compiled without error. Programs that are submitted to the server are run and a report is returned back, this report can be viewed in the program as a text file or the visualizer can be used to view the memory space as the program(s) execute. Spaces change colour in the visualizer as the program makes changes to memory spaces.

# Acknowledgements
The server and codemon language spec were provided by David McCaughan for use in this project
