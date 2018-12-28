---
title: Spirelands
---

# Spirelands #
Spirelands is a RPG game for PC written in Java. This project is maintained exclusively by me _Aaron Michael McNulty_, so new content gets added whenever I have time. The project uses [Gradle](https://gradle.org/ "Gradle Build Tool") for build automation and dependency management. If you intend on getting the source code from this repository, Gradle will be required to build the application see [Running Spirelands Locally With Gradle](#running-spirelands-locally-with-gradle).

### System Compatibility ###
Spirelands is beind developed for Windows 64 bit and 32 bit operating systems. The game uses OpenGL library so the system must be compatible with OpenGL version 2 or higher.

Spirelands is not compatible with Mac (OsX) operating systems at this time.

**Note**: _Spirelands has not been tested on any Linux platforms. It is unknown if it is fully compatible or not._

**Java:**

Java version 8 or higher is required to run the game. If you do not have Java installed on your machine you can get it from their website. I have provided the link here.
  * [Java Website](https://java.com/en/ "java.com")

## Where To Find The Game ##
Currently, there is not a distribution of the game available. Check back later for details on where you can find downloads for Sprirelands!

## Modding ##
If you have a mod for the game create a pull request to the **_modding_ branch** and I will take a look at adding it to the game. After mods have been added they will be part of the main game. At this point there isn't a mechanism in place to add mods externally to the game.

## Running Spirelands Locally With Gradle ##
In order to run the application locally you will first need to install [Gradle](https://gradle.org/ "Gradle Build Tool"). Standard Gradle commands can be used to clean, build, run, and create distributions of the application [Gradle Command-Line Interface](https://docs.gradle.org/current/userguide/command_line_interface.html "Gradle Docs | Command-Line Interface").

### Custom Gradle Tasks ###
There are custom Gradle tasks that have been added for more complex operations specific to the application.

#### Clean and run the application ####
This command will run the clean command then run command.
```
$ gradle cleanRun
```

#### Create distribution folders for all platforms ####
This command will create the distribution folders for each platform with their required dependencies. Each platform will be compressed into a zip folder.
```
$ gradle createDist
```

## Running Tests ##
Spirelands application comes with a test suite to test the public interface of various classes. Gradle is used to run the tests in the suite with the following command.

```
$ gradle test
```
This will run every test in the application. In order to run specific tests without running the whole suite you can use the following commands.
#### Run tests in specific package ####
This will run every test in the inventory package.
```
$ gradle test --tests com.monkeystomp.spirelands.inventory*
```
**Note:** _Asterisks can be used as a catch-all before or after package names. Also, the fully qualified package path is not required if the package name is unique. This can be seen in the following examples._
#### Run tests in specific file ####
This will run every test in the InventoryMangerTest.java file.
```
$ gradle test --tests InventoryManagerTest
```
#### Run a specific single test ####
This will run only the 'addArmorToInventory' test in the InventoryManagerTest.java file.
```
$ gradle test --tests InventoryManagerTest.addArmorToInventory
```
---
### Credits ###
<div style="display: flex;">
  <div style="flex-grow: 1;">
    Author:<br>
    Game Resources:
  </div>
  <div style="flex-grow: 3;">
    <i>Aaron Michael McNulty</i><br>
    <a href="https://opengameart.org/">Open Game Art</a>
  </div>
</div>