# Flag_backend
# Country Quiz API – Spring Boot 3 (JPA + REST)

## Description

Ce projet est une API **Spring Boot 3** qui permet :
- de **récupérer les pays**, leur **Code de drapeau**, leurs **continents** et **langues** depuis une API publique,
- de **stocker ces données en base** (via Spring Data JPA),
- de **récupérer ces pays selon leur continent ou leur langue officielle**
- de **gérer une base d'utilisateurs** (ajout, suppression avec mdp chiffrés)
- de **gérer une base de parties** qui contiennent les score des utilisateurs dans les mode de jeux

---

## Structure de la base de donnée

### Table country :
La table country est reliée en ManyToOne à la table continent et ManyToMany à la table language, elle comprends :
- **Id :** l'identifiant du pays dans la bdd
- **name :** le nom du pays
- **flag :** le code a 2 lettres qui permet d'identifier le pays et de recuperer le drapeau via une api utilisée dans le front
- **continent_id :** l'identifiant du continent auquel le pays appartient

### Table continent :
La table continent est reliée en OneToMany à la table country, elle comprends :
- **Id :** l'identifiant du continent dans la bdd
- **name :** le nom du continent

### Table language :
La table language est reliée en ManyToMany à la table country, elle comprends :
- **Id :** l'identifiant de la langue dans la bdd
- **name :** le nom de la langue
- **iso639_1 :** le code de la langue 

### Table country_language :
La table country_language est la table de liaison entre country et language, elle comprends :
- **country_id :** l'identifiant du pays dans la bdd
- **language_id :** l'identifiant de la langue associé au pays

### Table utilisateur :
La table utilisateur est reliée en OneToMany à la table game, elle comprends :
- **id :** l'identifiant de l'utilisateur
- **name :** le nom de l'utilisateur (c'est un champ unique)
- **email :** l'email de l'utilisateur (c'est un champ unique)
- **mdp :** le mot de passe de l'utilisateur (chiffré)
- **is_admin :** un boolean qui indique si l'utilisateur est un admin

### Table game :
La table game est reliée en ManyToOne à la table utilisateur, elle comprends :
- **id :** l'identifiant de la game
- **score :** le score de la partie en % (float entre 0.00 et 100.00)
- **catégorie :** la catégorie de la partie
- **game_date :** la date de sauvegarde de la partie
- **utilisateur_id :** l'identifiant de l'utilisateur qui a réalisé la partie

---
## Les Endpoints REST

### A remplir

---

## Exemple d’utilisation (via Postman)

### Pré-requis

- avoir le fichier .env bien configuré avec les variables suivantes :
  - DATABASE_USER=
  - DATABASE_PASSWORD=
  - DATABASE_NAME=
  - JWT_SECRET= clé secrete encodé en base64 (penser a vérifier que ça fini par un "=")
  - expiration_time= temps en miliseconde

- lancer le fichier schema.sql

  // Peut etre lancer un programme d'initialisation de la db dans un premier temps, l'arrêter, puis

- lancer l'application "BackSkeletonApplication"

###  Dans Postman
- `{{baseUrl}}` → `http://localhost:8080`

###  Créer un utilisateur
```http
POST {{baseUrl}}/api/auth/register
```
avec le raw json suivant
```http
{
  "name": "example",
  "email": "example1@example.com",
  "mdp": "123456",
  "is_admin": false
}
```

###  Se 'connecter' (récuperer un token pour accéder aux autres endpoints)
```http
POST {{baseUrl}}/api/auth/login
```
avec le raw json suivant
```http
{
  "name": "example",
  "mdp": "123456"
}
```
Il est pour la suite nécessaire de récuperer le token donné par la réponse et de le renseigner en selectionnant 'bearer token' dans authorization dans postman.

###  Lister les pays
```http
GET {{baseUrl}}/api/countries/
```
###  Lister les continents
```http
GET {{baseUrl}}/api/continents/
```
###  Lister les langues
```http
GET {{baseUrl}}/api/languages/
```
###  Lister les utilisateurs
```http
GET {{baseUrl}}/api/utilisateurs/
```
###  Lister les parties
```http
GET {{baseUrl}}/api/games/
```

... continuer avec tout les endpoints qui sont disponibles dans le code

---






