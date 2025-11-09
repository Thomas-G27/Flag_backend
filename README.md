# 🌍 Flag_backend

## Country Quiz API – Spring Boot 3 (JPA + REST + JWT)

## 🧩 Description

Ce projet est une API **Spring Boot 3** permettant de :

* **Récupérer et stocker des pays**, continents et langues depuis une API publique.
* **Gérer des utilisateurs** avec mots de passe chiffrés (BCrypt) et rôles (`is_admin`).
* **Gérer des parties (games)** associées aux utilisateurs.
* **Authentifier** les utilisateurs via **JWT Token**.
* **Protéger les endpoints** pour n’autoriser l’accès qu’aux utilisateurs connectés.

---

## 🗃️ Structure de la base de données

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

## 🚀 Endpoints REST

### 🔑 AUTHENTIFICATION

| Méthode | Endpoint             | Description                           | Corps de requête                                                                      | Auth requise |
| ------- | -------------------- | ------------------------------------- | ------------------------------------------------------------------------------------- | ------------ |
| `POST`  | `/api/auth/register` | Inscription d’un utilisateur          | `{ "name": "john", "email": "john@example.com", "mdp": "123456", "is_admin": false }` | ❌            |
| `POST`  | `/api/auth/login`    | Connexion + récupération du token JWT | `{ "name": "john", "password": "123456" }`                                            | ❌            |

🧠 **Réponse du login :**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

---

### 🧍 UTILISATEURS

| Méthode  | Endpoint                 | Description                                                        | Auth requise                          |
| -------- | ------------------------ |--------------------------------------------------------------------|---------------------------------------|
| `GET`    | `/api/utilisateurs/`     | Récupère tous les utilisateurs                                     | ✅                                     |
| `GET`    | `/api/utilisateurs/{id}` | Récupère un utilisateur par ID                                     | ✅                                     |
| `POST`   | `/api/utilisateurs/`     | Crée un nouvel utilisateur (équivalent à register, utile si admin) | ✅                                     |
| `DELETE` | `/api/utilisateurs/{id}` | Supprime un utilisateur                                            | ✅ *(admin dans une prochaine version)* |

---

### 🎮 GAMES (parties)

| Méthode  | Endpoint                            | Description                                 | Corps / Paramètres                                                   | Auth requise                           |
| -------- | ----------------------------------- | ------------------------------------------- |----------------------------------------------------------------------|----------------------------------------|
| `GET`    | `/api/games/`                       | Liste toutes les parties                    | -                                                                    | ✅                                      |
| `GET`    | `/api/games/{id}`                   | Récupère une partie par son ID              | -                                                                    | ✅                                      |
| `GET`    | `/api/games/utilisateur/{username}` | Récupère les parties d’un utilisateur donné | -                                                                    | ✅                                      |
| `GET`    | `/api/games/categorie/{categorie}`  | Récupère les parties selon une catégorie    | -                                                                    | ✅                                      |
| `POST`   | `/api/games/`                       | Ajoute une partie                           | `{ "score": 85.5, "categorie": "europe", "utilisateurName": "john" }` | ✅                                      |
| `DELETE` | `/api/games/{id}`                   | Supprime une partie par ID                  | -                                                                    | ✅ *(admin dans une prochaine version)* |

---

### 🌍 COUNTRIES (pays)

| Méthode | Endpoint                                | Description                              | Auth requise |
| ------- | --------------------------------------- | ---------------------------------------- | ------------ |
| `GET`   | `/api/countries/`                       | Liste tous les pays                      | ✅            |
| `GET`   | `/api/countries/{id}`                   | Récupère un pays par ID                  | ✅            |
| `GET`   | `/api/countries/code/{code}`            | Récupère un pays par code ISO            | ✅            |
| `GET`   | `/api/continents/{continent}/countries` | Liste les pays d’un continent donné      | ✅            |
| `GET`   | `/api/languages/{language}/countries`   | Liste les pays parlant une langue donnée | ✅            |

---

### 🗺️ CONTINENTS

| Méthode | Endpoint               | Description                                                                   | Auth requise |
| ------- | ---------------------- |-------------------------------------------------------------------------------| ------------ |
| `GET`   | `/api/continents/`     | Liste tous les continents                                                     | ✅            |
| `GET`   | `/api/continents/{id}` | Récupère un continent par ID et affiche les noms des pays qui lui appartienne | ✅            |

---

### 🗣️ LANGUAGES

| Méthode | Endpoint              | Description                                                                     | Auth requise |
| ------- | --------------------- |---------------------------------------------------------------------------------| ------------ |
| `GET`   | `/api/languages/`     | Liste toutes les langues                                                        | ✅            |
| `GET`   | `/api/languages/{id}` | Récupère une langue par ID et affiche les nom des pays qui perlent cette langue | ✅            |

---

## ⚙️ Exemple d’utilisation via Postman

### 1️⃣ Inscription

```http
POST http://localhost:8080/api/auth/register
```

Body (JSON) :

```json
{
  "name": "example",
  "email": "example@example.com",
  "mdp": "123456",
  "is_admin": false
}
```

### 2️⃣ Connexion

```http
POST http://localhost:8080/api/auth/login
```

Body (JSON) :

```json
{
  "name": "example",
  "password": "123456"
}
```

➡️ Récupérer le `token` dans la réponse et le mettre dans **Authorization → Bearer Token**

### 3️⃣ Accès à un endpoint protégé

```http
GET http://localhost:8080/api/games/
Authorization: Bearer <votre_token>
```

---

## 🔐 Sécurité & Authentification

* Authentification gérée via **JWT Token**
* Les endpoints `/api/auth/**` sont publics
* Tous les autres endpoints nécessitent un **token valide**
* Les mots de passe sont **hashés avec BCrypt**
* Le token contient un claim `isAdmin` permettant de vérifier les privilèges

---

## ⚙️ Variables d’environnement (.env)

Créer un fichier `.env` à la racine du projet backend :

```env
DATABASE_USER=
DATABASE_PASSWORD=
DATABASE_NAME=
JWT_SECRET=LaCleSecreteEncodeeBase64=
EXPIRATION_TIME=360000
```

---

## 🏗️ Lancer le projet

1. Importer le projet dans IntelliJ
2. Vérifier le fichier `.env` (le demander si nécessaire, les valeurs ne sont pas les meme que dans ce readme)
3. Vérifier que le conteneur Dokcer tourne bien
4. Lancer la classe `BackSkeletonApplication`
5. L’API tourne sur :
   👉 [http://localhost:8080](http://localhost:8080)

---

