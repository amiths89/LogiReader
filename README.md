# BinFileReader

A simple Java application that reads and parses a custom binary log file (`logi.bin`), extracts messages, and generates a formatted HTML report for easy viewing.

## Table of Contents

* [Features](#features)
* [Prerequisites](#prerequisites)
* [Getting Started](#getting-started)
* [File Structure](#file-structure)
* [How It Works](#how-it-works)
* [Future Improvements](#future-improvements)

---

## Features

* **Binary File Parsing:** Reads a `.bin` file with a custom format (4-byte message size, 4-byte sequence number, and UTF-8 payload).
* **HTML Report Generation:** Creates a clean, well-styled, and responsive HTML report by programmatically building the HTML string.
* **Cross-Platform Compatibility:** Uses Java's `Desktop` class to automatically open the generated report in the default web browser.

---

## Prerequisites

* Java Development Kit (JDK) 11 or higher
* Maven

---

## Getting Started

1.  **Clone the Repository**

    ```sh
    git clone [https://github.com/amiths89/LogiReader.git](https://github.com/amiths89/LogiReader.git)
    cd LogiReader
    cd BinFileReader
    ```

2.  **Add the `logi.bin` File**

    Place your `logi.bin` binary file inside the `src/main/resources/` directory.

3.  **Build and Run the Project**

    You can run the `Main` class directly from your IDE or use the following Maven commands:

    ```sh
    mvn clean install
    mvn exec:java -Dexec.mainClass="org.java.bin.Main"
    ```

    The application will process the binary file and automatically open the `report.html` file in your default web browser.

---

## File Structure

* `src/main/java/org/java/bin/`
    * `Main.java`: The main application entry point that reads the binary file and calls the HTML generator.
    * `Message.java`: A simple immutable class representing a message object.
    * `HtmlGenerator.java`: A class that manually generates the HTML report as a string.
* `src/main/resources/`
    * `logi.bin`: The binary log file to be processed.
* `pom.xml`: The Maven project configuration file.
* `README.md`: This file.

---

## How It Works

1. `Main.java` starts the process by calling `readMessage` to parse the `logi.bin` file.
2. The `readMessage` method reads the binary data, extracts the size, sequence number, and payload, and populates a list of `Message` objects.
3. The `HtmlGenerator` method then manually constructs the HTML as a single string using `StringBuilder`.
4. Finally, `Main.java` saves the generated HTML content to `report.html` and opens it.

---
