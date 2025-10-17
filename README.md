# Flag_backend
# Country Quiz API – Spring Boot 3 (JPA + REST)

## Description

Ce projet est une API **Spring Boot 3** qui permet :
- de **récupérer les pays**, leurs **continents** et **langues** depuis une API publique,
- de **stocker ces données en base** (via Spring Data JPA),
- de **créer et gérer des parties de quiz** avec des questions générées automatiquement à partir de la base.


---


## Endpoints REST

### Pays
 `POST`
`/api/pays/seed` : Charge les pays/continents/langues depuis l’API externe 
`GET`  `/api/pays` : Liste tous les pays (avec continent et langues) |

---

### Parties
| `POST`  `/api/parties/start` :Démarre une nouvelle partie |
| `GET` `/api/parties/{id}/next-question` : Récupère la prochaine question non répondue |
| `POST` `/api/parties/{id}/questions/{qid}/answer` : Envoie une réponse à une question |
| `GET`  `/api/parties/{id}` :Récapitulatif final de la partie |
| `GET`  `/api/parties` : Liste toutes les parties existantes |

---

## Exemple d’utilisation (via Postman)


###  Variables Postman
- `{{baseUrl}}` → `http://localhost:8081`

### Charger les pays
```http
POST {{baseUrl}}/api/pays/seed
```

###  Lister les pays
```http
GET {{baseUrl}}/api/pays
```

### Démarrer une partie (aléatoire)
```http
POST {{baseUrl}}/api/parties/start
Content-Type: application/json

{
  "date": "2025-10-16 11:00",
  "nbQuestions": 5,
  "countryIds": []
}
```

### Prochaine question
```http
GET {{baseUrl}}/api/parties/1/next-question
```

###  Répondre à une question
```http
POST {{baseUrl}}/api/parties/1/questions/10/answer
Content-Type: application/json

{
  "option": "A"
}
```

### Voir le résumé de la partie
```http
GET {{baseUrl}}/api/parties/1
```

---

##  Tests rapides en cURL

```bash
# Charger les pays
curl -X POST http://localhost:8081/api/pays/seed

# Lister les pays
curl http://localhost:8081/api/pays

# Démarrer une partie
curl -X POST http://localhost:8081/api/parties/start -H "Content-Type: application/json" -d '{"date":"2025-10-16 11:00","nbQuestions":5,"countryIds":[]}'
```

---






