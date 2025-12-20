# AnimalDex

---

## Auteurs
- Romain Durussel
- Abram Zweifel

HEIG-VD, Class C, 2025–2026

---

## Table des matières

- [Description](#description)
- [Instructions de Build](#instructions-de-build)
- [Instructions de Run](#instructions-de-run)

---

## Description
Dans le cadre de ce travail pratique, nous souhaitons développer une application web permettant de gérer un “pokedex” d’animaux observés au cours de sa vie.

Une API accessible publiquement permettra d'offrir deux ressources principales : 
- les animaux,
- des observations.

L’utilisateur pourra ainsi enregistrer de nouveaux animaux (numéro unique, nom français, nom latin, groupe taxonomique, etc.) puis créer des observations associées, en indiquant la date, la région et des notes éventuelles.

L’API permettra d’effectuer des opérations CRUD sur les deux ressources et proposera également des filtres (par espèce, région, date).
Le service sera déployé sur une machine virtuelle et accessible via un nom de domaine.

---

## Instructions de Build

Le projet inclut un **Maven Wrapper**, ce qui signifie que vous n'avez pas besoin d'installer Maven manuellement.
Seul le JDK 21 est requis.

### 1. Cloner le projet :

```bash
git clone https://github.com/romain-drsl/AnimalDex.git

cd AnimalDex
 ```

### 2. Compiler et construire le JAR :

Sous Linux, macOS ou WSL :

```Bash
./mvnw clean package
```

Sous Windows (CMD ou PowerShell) :

```Bash
./mvnw.cmd clean package
```

Une fois la compilation terminée, le fichier JAR exécutable se trouve ici : `target/AnimalDex-1.0-SNAPSHOT.jar`

---

## Instructions de Run

```Bash
java -jar target/AnimalDex-1.0-SNAPSHOT.jar
```

---