# Memory-File-System
**OVERVIEW
 This project is an in-memory file system implemented in Java, providing various functionalities  to a terminal-based file system. It allows users to interact via a command-line interface to perform all the 
  operations such as mkdir, cd, ls grep , cat, touch, mv ,rm,  echo, cp


# Implementation Details
Classes Used:
        File: Represents a file with content.
        Directory: Represents a directory containing files and subdirectories.
        FileSystem: Manages the file system operations and provides a command-line interface for user interaction.
# Functionalities Supported:
        mkdir: Create a new directory.
        cd: Change the current directory.
        ls: List contents of directories.
        grep: Search for patterns in files
        cat: Display file contents.
        touch: Create an empty file.
        echo: Write text to a file.
        mv: Move files/directories.
        cp: Copy files/directories.
        rm: Remove files/directories.
    
# Data Structures Used:
       HashMap: Used to store files and directories for quick access and management.
# Command-Line Interface:
      Utilizes Scanner for user input to perform operations dynamically.
# Setup Instructions
   **Prerequisites:
       Java Development Kit (JDK) installed.
# Setup Steps:
    -> Clone or download the project repository.
    ->Open the project directory in your preferred IDE or text editor (e.g., VS Code).
    ->Compile the FileSystem.java file using the command: javac FileSystem.java.(in terminal)
    ->Run the compiled program using: java FileSystem.(in terminal)
# Program Execution Overview
        ->Type Commands: Enter various commands (mkdir, cd, ls, etc.) to interact with the file system.
        ->Infinite Loop: The program operates in an infinite loop, continuously prompting for commands.
        ->Exit Condition: Input exit to terminate the program and exit the infinite loop.
