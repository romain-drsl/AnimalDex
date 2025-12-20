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

## Contrat API

L’API AnimalDex utilise le protocole HTTP sur le port XXXX.

Le format JSON est utilisé pour l’échange de données.

L’API est basée sur le modèle CRUD et expose deux ressources principales.

### Ressource 1 - Animaux

Représentation des animaux.

#### Modèle de données

- `number` : numéro dans le AnimalDex
  - unique, 
  - généré automatiquement. 
- `frenchName` : nom français de l'animal
  - obligatoire.
- `latinName` : nom latin de l'animal
  - obligatoire.
- `group` : groupe taxonomique de l'animal
  - valeurs : MAMMAL, BIRD, REPTILE, AMPHIBIAN, FISH, INSECT, ARACHNID ou OTHER,
  - obligatoire.

#### Créer un animal

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"frenchName":"Renard roux","latinName":"Vulpes vulpes","group":"MAMMAL"}' \
  https://<domain>/animals
```

Sortie (201 Created) :

```json
{
  "number": 1,
  "frenchName": "Renard roux",
  "latinName": "Vulpes vulpes",
  "group": "MAMMAL"
}
```

#### Afficher la liste de tous les animaux

```bash
curl https://<domain>/animals
```

Sortie (200 OK) :

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
  // Tous les autres animaux ...
]
```

#### Afficher un animal spécifique

```bash
curl https://<domain>/animals/{number}
```

#### Filtrer les animaux

Le filtre peut être appliqué sur un ou plusieurs des champs (query parameters) suivants :

- `frenchName` : Filtrer par nom français (recherche partielle autorisée).
- `latinName` : Filtrer par nom latin (recherche partielle autorisée).
- `group` : Filtrer par groupe taxonomique.

```bash
# Recherche par nom français
curl "https://<domain>/animals?frenchName=renard"

# Recherche par nom latin
curl "https://<domain>/animals?latinName=vulpes"

# Seulement les oiseaux
curl "https://<domain>/animals?group=BIRD"

# Combiner plusieurs filtres
curl "https://<domain>/animals?group=MAMMAL&frenchName=renard"
```

#### Mettre à jour un animal

```bash
curl -X PUT \
  -H "Content-Type: application/json" \
  -d '{"frenchName":"Renard (roux)","latinName":"Vulpes vulpes","group":"MAMMAL"}' \
  https://<domain>/animals/1
```

Sortie (200 OK) :

```json
{
  "number": 1,
  "frenchName": "Renard (roux)",
  "latinName": "Vulpes vulpes",
  "group": "MAMMAL"
}
```

#### Supprimer un animal

```bash
curl -X DELETE https://<domain>/animals/1
```

Sortie (204 No Content) :

```text
HTTP/2 204
date: Sat, 20 Dec 2025 14:40:00 GMT
```

### Ressource 2 - Observations

Représentation des observations pour un animal donné.

#### Modèle de données
- `id` : identifiant unique de l'observation
  - unique,
  - généré automatiquement.
- `animalNumber` : numéro de l'animal observé
  - obligatoire,
  - doit correspondre à un animal existant.
- `date` : date de l'observation 
  - format : `yyyy-mm-dd`,
  - obligatoire,
  - doit être une date valide.
- `region` : région de l'observation
  -  facultatif.
- `notes` : notes supplémentaires sur l'observation
  - facultatif.

#### Créer une observation

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"animalNumber":1,"date":"15-08-2025","region":"Suisse","notes":"Vu en lisière de forêt"}' \
  https://<domain>/observations
```

Sortie (201 Created) :

```json
{
  "id": 1,
  "animalNumber": 1,
  "date": "15-08-2025",
  "region": "Suisse",
  "notes": "Vu en lisière de forêt"
}
```

#### Afficher la liste de toutes les observations

```bash
curl https://<domain>/observations
```

Sortie (200 OK) :

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
  // Toutes les autres observations ...
]
```

#### Afficher une observation spécifique

```bash
curl https://<domain>/observations/{id}
```

#### Filtrer les observations

Le filtre peut être appliqué sur un ou plusieurs des champs (query parameters) suivants :
- `animalNumber` : Filtrer par numéro d'animal.
- `date` : Filtrer par date des observations.
- `region` : Filtrer par région (recherche partielle autorisée).

```bash
# Filtrer par numéro d'animal
curl "https://<domain>/observations?animalNumber=1"

# Filtrer par date
curl "https://<domain>/observations?date=15-08-2025"

# Filtrer par région
curl "https://<domain>/observations?region=Suisse"

# Combiner plusieurs filtres
curl "https://<domain>/observations?animalNumber=1&region=Suisse"
```

#### Mettre à jour une observation

```bash
curl -X PUT \
  -H "Content-Type: application/json" \
  -d '{"animalNumber":1,"date":"16-08-2025","region":"Suisse","notes":"Vu près d\'un ruisseau"}' \
  https://<domain>/observations/1
```

Sortie (200 OK) :

```json
{
  "id": 1,
  "animalNumber": 1,
  "date": "16-08-2025",
  "region": "Suisse",
  "notes": "Vu près d'un ruisseau"
}
```

#### Supprimer une observation

```bash
curl -X DELETE https://<domain>/observations/1
```

Sortie (204 No Content) :

```text
HTTP/2 204
date: Sat, 20 Dec 2025 14:50:00 GMT
```

### Erreurs

En cas d'erreur, l'API retournera :

- Un code HTTP approprié 
  - `400 Bad Request` pour les erreurs de validation,
  - `404 Not Found` si la ressource n'existe pas,
  - `409 Conflict` en cas de conflit.
- Un message d'erreur au format JSON.

Exemple :

```json
{
"error": "400 Bad Request",
"message": "date must be in format yyyy-mm-dd"
}
```

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