# AnimalDex

---

## Auteurs
- Romain Durussel
- Abram Zweifel

HEIG-VD, Class C, 2025–2026

---

## Table des matières

- [Description](#description)
- [Contrat API](#contrat-api)
- [Ressource 1 - Animaux](#ressource-1---animaux)
- [Ressource 2 - Observations](#ressource-2---observations)
- [Instructions de Build](#instructions-de-build)
- [Instructions de Run](#instructions-de-run)

---

## Description
Dans le cadre de ce travail pratique, nous souhaitons développer une application web permettant de gérer un “pokedex” d’animaux observés au cours de sa vie.

Une API accessible publiquement permettra d'offrir deux ressources principales : 
- les animaux,
- des observations.

L’utilisateur pourra ainsi enregistrer de nouveaux animaux puis créer des observations associées, en indiquant la date, la région et des notes éventuelles.

Des fonctionnalités de recherche et de filtrage sont également proposées pour retrouver facilement des animaux ou des observations spécifiques.

---

## Contrat API

L’API AnimalDex utilise le protocole HTTP sur le port 8080.

Le format JSON est utilisé pour l’échange de données.

L’API permettra d’effectuer des opérations CRUD sur les deux ressources.

Le service sera déployé sur une machine virtuelle et accessible via un nom de domaine.

## Ressource 1 - Animaux

Représentation des animaux. Propose les opérations suivantes :

- Créer un nouvel animal
- Obtenir plusieurs animaux avec possibilité de filtrage
- Obtenir un animal spécifique à partir de son numéro
- Mettre à jour un animal
- Supprimer un animal

### Créer un nouvel animal

`POST /animals`

Créer un nouvel animal dans l’AnimalDex.

#### Requête

Le corps de la requête doit contenir un objet JSON avec les propriétés suivantes :

- `frenchName` – Nom français de l’animal
- `latinName` – Nom latin de l’animal
- `group` – Groupe taxonomique de l’animal
  - Valeurs possibles : `MAMMAL`, `BIRD`, `REPTILE`, `AMPHIBIAN`, `FISH`, `INSECT`, `ARACHNID` ou `OTHER`

Exemple :

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"frenchName":"Renard roux","latinName":"Vulpes vulpes","group":"MAMMAL"}' \
  https://<domain>/animals
```

#### Réponse

Le corps de la réponse contient un objet JSON avec les propriétés suivantes :

- `number` – Numéro unique de l’animal dans le AnimalDex (généré automatiquement)
- `frenchName` – Nom français de l’animal
- `latinName` – Nom latin de l’animal
- `group` – Groupe taxonomique de l’animal

#### Codes de statut

- `201` (Created) – L’animal a été créé avec succès
- `400` (Bad Request) – Le corps de la requête est invalide
- `409` (Conflict) – Conflit métier

Exemple de sortie (201 Created) :

```json
{
  "number": 1,
  "frenchName": "Renard roux",
  "latinName": "Vulpes vulpes",
  "group": "MAMMAL"
}
```

### Obtenir plusieurs animaux

`GET /animals`

Obtenir la liste des animaux enregistrés dans l’AnimalDex.

#### Requête

La requête peut être appliquée sur un ou plusieurs des champs (query parameters) suivants :

- `frenchName` – Filtrer par nom français (recherche partielle autorisée)
- `latinName` – Filtrer par nom latin (recherche partielle autorisée)
- `group` – Filtrer par groupe taxonomique

Exemples :

```bash
# Liste complète des animaux
curl https://<domain>/animals

# Recherche par nom français
curl "https://<domain>/animals?frenchName=renard"

# Recherche par nom latin
curl "https://<domain>/animals?latinName=vulpes"

# Seulement les oiseaux
curl "https://<domain>/animals?group=BIRD"

# Combiner plusieurs filtres
curl "https://<domain>/animals?group=MAMMAL&frenchName=renard"
```

#### Réponse

Le corps de la réponse contient un tableau JSON avec les propriétés suivantes :

- `number` – Numéro unique de l’animal dans le AnimalDex (généré automatiquement)
- `frenchName` – Nom français de l’animal
- `latinName` – Nom latin de l’animal
- `group` – Groupe taxonomique de l’animal

#### Codes de statut

- `200` (OK) – Les animaux ont été récupérés avec succès

Exemple de sortie (200 OK) :

```json
[
  {
    "number": 1,
    "frenchName": "Renard roux",
    "latinName": "Vulpes vulpes",
    "group": "MAMMAL"
  },
  {
    "number": 2,
    "frenchName": "Hirondelle rustique",
    "latinName": "Hirundo rustica",
    "group": "BIRD"
  }
  // Les autres animaux ...
]
```

### Obtenir un animal spécifique

`GET /animals/{number}`

Obtenir un animal à partir de son numéro.

#### Requête

Le chemin de la requête doit contenir le numéro de l’animal.

Exemple :

```bash
curl https://<domain>/animals/5
```

#### Réponse

Le corps de la réponse contient un tableau JSON avec les propriétés suivantes :

- `number` – Numéro unique de l’animal dans le AnimalDex (généré automatiquement)
- `frenchName` – Nom français de l’animal
- `latinName` – Nom latin de l’animal
- `group` – Groupe taxonomique de l’animal

#### Codes de statut

- `200` (OK) – L’animal a été récupéré avec succès
- `404` (Not Found) – L’animal n’existe pas

### Mettre à jour un animal

`PUT /animals/{number}`

Mettre à jour les informations d’un animal existant.

#### Requête

Le chemin de la requête doit contenir le numéro de l’animal.

Le corps de la requête doit contenir un objet JSON avec les propriétés suivantes :

- `frenchName` – Nom français de l’animal
- `latinName` – Nom latin de l’animal
- `group` – Groupe taxonomique de l’animal

Exemple :

```bash
curl -X PUT \
  -H "Content-Type: application/json" \
  -d '{"frenchName":"Renard (roux)","latinName":"Vulpes vulpes","group":"MAMMAL"}' \
  https://<domain>/animals/1
```

#### Réponse

Le corps de la réponse contient un tableau JSON avec les propriétés suivantes :

- `number` – Numéro unique de l’animal dans le AnimalDex (généré automatiquement)
- `frenchName` – Nom français de l’animal
- `latinName` – Nom latin de l’animal
- `group` – Groupe taxonomique de l’animal

#### Codes de statut

- `200` (OK) – L’animal a été récupéré avec succès
- `400` (Bad Request) – Le corps de la requête est invalide
- `404` (Not Found) – L’animal n’existe pas

### Supprimer un animal

`DELETE /animals/{number}`

Supprimer un animal existant de l’AnimalDex à partir de son numéro.

#### Requête

Le chemin de la requête doit contenir le numéro de l’animal.

Exemple :

```bash
curl -X DELETE https://<domain>/animals/1
```

#### Réponse

Le corps de la réponse est vide.

#### Codes de statut

- `204` (No Content) – L’animal a été supprimé avec succès
- `404` (Not Found) – L’animal n’existe pas

## Ressource 2 - Observations

Représentation des observations pour un animal donné. Propose les opérations suivantes :

- Créer une observation
- Obtenir plusieurs observations avec possibilité de filtrage
- Obtenir une observation à partir de son identifiant
- Mettre à jour une observation
- Supprimer une observation

### Créer une observation

`POST /observations`

Créer une nouvelle observation associée à un animal existant.

#### Requête

Le corps de la requête doit contenir un objet JSON avec les propriétés suivantes :

- `animalNumber` – Numéro de l’animal observé
- `date` – Date de l’observation au format `YYYY-MM-DD`
- `region` – Région de l’observation (facultatif)
- `notes` – Notes complémentaires (facultatif)

Exemple :

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"animalNumber":1,"date":"15-08-2025","region":"Suisse","notes":"Vu en lisière de forêt"}' \
  https://<domain>/observations
```

#### Réponse

Le corps de la réponse contient un objet JSON avec les propriétés suivantes :

- `id` – Identifiant unique de l’observation (généré automatiquement)
- `animalNumber` – Numéro de l’animal observé
- `date` – Date de l’observation au format `YYYY-MM-DD`
- `region` – Région de l’observation (facultatif)
- `notes` – Notes complémentaires (facultatif)

#### Codes de statut

- `201` (Created) – L’observation a été créée avec succès
- `400` (Bad Request) – Le corps de la requête est invalide (ex. format de date incorrect)
- `404` (Not Found) – L’animal associé n’existe pas

Exemple de sortie (201 Created) :

```json
{
  "id": 1,
  "animalNumber": 1,
  "date": "15-08-2025",
  "region": "Suisse",
  "notes": "Vu en lisière de forêt"
}
```

### Obtenir plusieurs observations

`GET /observations`

Obtenir la liste des observations enregistrées dans l’AnimalDex.

#### Requête

La requête peut être appliquée sur un ou plusieurs des champs (query parameters) suivants :
- `animalNumber` : Filtrer par numéro d'animal.
- `date` : Filtrer par date des observations.
- `region` : Filtrer par région des observations (recherche partielle autorisée).

Exemples :

```bash
# Liste complète des observations
curl https://<domain>/observations

# Filtrer par numéro d'animal
curl "https://<domain>/observations?animalNumber=1"

# Filtrer par date
curl "https://<domain>/observations?date=15-08-2025"

# Filtrer par région
curl "https://<domain>/observations?region=Suisse"

# Combiner plusieurs filtres
curl "https://<domain>/observations?animalNumber=1&region=Suisse"
```

#### Réponse

Le corps de la réponse contient un tableau JSON avec les propriétés suivantes :
- `id` – Identifiant unique de l’observation (généré automatiquement)
- `animalNumber` – Numéro de l’animal observé
- `date` – Date de l’observation au format `YYYY-MM-DD`
- `region` – Région de l’observation (facultatif)
- `notes` – Notes complémentaires (facultatif)

#### Codes de statut

- `200` (OK) – Les observations ont été récupérées avec succès

Exemple de sortie (200 OK) :

```json
[
  {
    "id": 1,
    "animalNumber": 2,
    "date": "15-08-2025",
    "region": "Suisse",
    "notes": "Vu en lisière de forêt"
  },
  {
    "id": 2,
    "animalNumber": 2,
    "date": "20-09-2025",
    "region": "Canada",
    "notes": ""
  }
  // Les autres observations ...
]
```

### Obtenir une observation spécifique

`GET /observations/{id}`

Obtenir une observation spécifique à partir de son identifiant.

#### Requête

Le chemin de la requête doit contenir l’identifiant de l’observation.

Exemple :

```bash
curl https://<domain>/observations/3
```

#### Réponse

Le corps de la réponse contient un objet JSON avec les propriétés suivantes :

- `id` – Identifiant unique de l’observation (généré automatiquement)
- `animalNumber` – Numéro de l’animal observé
- `date` – Date de l’observation au format `YYYY-MM-DD`
- `region` – Région de l’observation (facultatif)
- `notes` – Notes complémentaires (facultatif)

#### Codes de statut

- `200` (OK) – L’observation a été récupérée avec succès
- `404` (Not Found) – L’observation n’existe pas

### Mettre à jour une observation

`PUT /observations/{id}`

Mettre à jour les informations d’une observation existante.

#### Requête

Le chemin de la requête doit contenir l’identifiant de l’observation.

Le corps de la requête doit contenir un objet JSON avec les propriétés suivantes :

- `animalNumber` – Numéro de l’animal observé
- `date` – Date de l’observation au format `YYYY-MM-DD`
- `region` – Région de l’observation (facultatif)
- `notes` – Notes complémentaires (facultatif)

Exemple :

```bash
curl -X PUT \
  -H "Content-Type: application/json" \
  -d '{"animalNumber":1,"date":"16-08-2025","region":"Suisse","notes":"Vu près d\'un ruisseau"}' \
  https://<domain>/observations/1
```

#### Réponse

Le corps de la réponse contient un objet JSON avec les propriétés suivantes :

- `id` – Identifiant unique de l’observation (généré automatiquement)
- `animalNumber` – Numéro de l’animal observé
- `date` – Date de l’observation au format `YYYY-MM-DD`
- `region` – Région de l’observation (facultatif)
- `notes` – Notes complémentaires (facultatif)

#### Codes de statut

- `200` (OK) – L’observation a été mise à jour avec succès
- `400` (Bad Request) – Le corps de la requête est invalide (ex. format de date incorrect)
- `404` (Not Found) – L’observation ou l’animal associé n’existe pas

### Supprimer une observation

`DELETE /observations/{id}`

Supprimer une observation existante à partir de son identifiant.

#### Requête

Le chemin de la requête doit contenir l’identifiant de l’observation.

Exemple :

```bash
curl -X DELETE https://<domain>/observations/1
```

#### Réponse

Le corps de la réponse est vide.

#### Codes de statut

- `204` (No Content) – L’observation a été supprimée avec succès
- `404` (Not Found) – L’observation n’existe pas

---

## Instructions de Build

Le projet inclut un **Maven Wrapper**, ce qui signifie que vous n'avez pas besoin d'installer Maven manuellement.
Seul Java JDK 21 doit être installé sur votre machine.

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