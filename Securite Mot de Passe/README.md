# Securite Mot de Passe — Password Recovery Research Project

Small Java project inspired by John the Ripper. The goal is to provide a program that helps recover plain-text passwords from hashes using wordlists and configurable cracking strategies. This repository contains a JavaFX GUI and core logic implemented in Java.

**Features**
- Simple JavaFX GUI launcher with hash history and wordlist support.
- Wordlist-driven password candidate generation.
- Educational codebase for learning password-recovery techniques.

**Quick Links**
- Main application: [src/main/java/com/eugene/securite_mo_de_passe/MainApp.java](src/main/java/com/eugene/securite_mo_de_passe/MainApp.java)
- GUI controllers: [src/main/java/com/eugene/securite_mo_de_passe/view/SecuriteOverviewController.java](src/main/java/com/eugene/securite_mo_de_passe/view/SecuriteOverviewController.java)
- Model: [src/main/java/com/eugene/securite_mo_de_passe/model/Securite.java](src/main/java/com/eugene/securite_mo_de_passe/model/Securite.java)
- Wordlist: [ressource/word%20List.txt](ressource/word%20List.txt)

**Prerequisites**
- JDK 8 (project originally developed for Java 8 / JavaFX on that platform)
- Maven 3.x for building (optional if running inside an IDE)

**Build & Run**
- From an IDE (recommended for development): open the project and run the `MainApp` class.
- Build with Maven:

```
mvn clean package
```

- If an artifact JAR is produced, run it with:

```
java -jar path/to/Securite_Mot_de_Passe.jar
```

**Notes**
- In this project some parts are for demonstration and learning rather than production use.

**Resources**
- Wordlists and other assets are under the `ressource/` directory.

**License**
- See the `LICENSE` file in the project root.

**Contributing / Running**
- This is an educational repository. If you make improvements (tests, modernize to newer Java, document algorithms), please open a PR or keep your own fork.

Merci / Thanks — Eugène ETOUNDI
