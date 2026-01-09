# E-Commerce Spring Boot API

## Description

API REST backend développée avec Spring Boot pour la gestion d'une plateforme e-commerce. Cette API permet de gérer les utilisateurs, les produits, les commandes, les paiements (simulés) et les livraisons.

## Portée du système

Le système permet de gérer la vente de produits en ligne avec les fonctionnalités suivantes :
- Consultation du catalogue de produits
- Gestion des utilisateurs et des rôles
- Gestion des commandes et des paiements (simulés)
- Gestion des livraisons
- Administration de la plateforme

Le projet adopte une architecture orientée services (API REST) afin de permettre une intégration avec plusieurs clients (Web, Mobile, etc.).

## Technologies utilisées

### Backend
- **Java 21**
- **Spring Boot 3.5.9**
- **Spring Data JPA / Hibernate**
- **Spring Security** (JWT)
- **PostgreSQL**
- **Liquibase** (migrations de base de données)
- **MapStruct** (mapping d'objets)
- **Lombok**
- **SpringDoc OpenAPI** (documentation API)
- **Testcontainers** (tests d'intégration)

### Outils de développement
- **Maven**

## Prérequis

- Java 21 ou supérieur
- Maven 3.6+
- PostgreSQL 12+
- Git

## 🚀 Installation et Configuration

### 1. Cloner le repository

```bash
git clone <url-du-repository>
cd e-com-spring
```

### 2. Configurer la base de données

Créer une base de données PostgreSQL :

```sql
CREATE DATABASE e_com_spring;
```

### 3. Configurer l'application

Modifier le fichier `src/main/resources/application.yml` selon votre environnement :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/e_com_spring
    username: votre_utilisateur
    password: votre_mot_de_passe
```

### 4. Lancer l'application

```bash
./mvnw spring-boot:run
```

Ou avec Maven installé :

```bash
mvn spring-boot:run
```

L'application sera disponible sur `http://localhost:8080`

## Documentation API

Une fois l'application démarrée, la documentation Swagger/OpenAPI est accessible via :

```
http://localhost:8080/swagger-ui.html
```

## Classes d'utilisateurs

Le système gère trois types d'utilisateurs :

- **CLIENT** : Consulte les produits, gère son panier, passe des commandes
- **ADMIN** : Gère le système (produits, commandes, utilisateurs)
- **LIVREUR** : Gère l'état des livraisons

## Authentification et Autorisation

Le système utilise JWT (JSON Web Token) pour l'authentification :
- Inscription des utilisateurs
- Connexion avec email et mot de passe
- Génération d'un token JWT après authentification
- Gestion des rôles (CLIENT, ADMIN, LIVREUR)
- Protection des endpoints par JWT

## Fonctionnalités principales

### Authentification et Autorisation
- Inscription des utilisateurs
- Connexion avec email et mot de passe
- Génération de token JWT
- Gestion des rôles (CLIENT, ADMIN, LIVREUR)

### Gestion des utilisateurs
- Consultation de la liste des utilisateurs (Admin)
- Activation/désactivation d'un utilisateur (Admin)

### Gestion des produits
- CRUD complet sur les produits (Admin)
- Gestion des catégories de produits
- Consultation du catalogue (Clients)

### Panier
- Ajout de produits au panier
- Modification de la quantité
- Suppression de produits du panier

### Commandes
- Validation de commandes
- Calcul automatique du montant total
- Consultation de l'historique des commandes

### Paiement
- ⚠️ **Simulation de paiement** (service externe non intégré)
- Enregistrement du statut du paiement

### Livraison
- Attribution d'une commande à un livreur (Admin)
- Mise à jour du statut de livraison (Livreur)

## 🔒 Exigences non fonctionnelles

### Performance
- L'API doit répondre en moins de 2 secondes pour 95% des requêtes

### Sécurité
- Mots de passe chiffrés (BCrypt)
- Protection des endpoints par JWT
- Vérification des rôles à chaque requête
- Conformité aux standards OWASP

### Disponibilité
- Le système doit être disponible 99% du temps

### Scalabilité
- Architecture supportant la montée en charge horizontale

### Maintenabilité
- Architecture claire et modulaire
- Code respectant les bonnes pratiques

## Architecture

Le projet suit une architecture modulaire avec :
- **Controllers** : Points d'entrée de l'API REST
- **Services** : Logique métier
- **Repositories** : Accès aux données (JPA)
- **Models/Entities** : Entités de la base de données
- **DTOs** : Objets de transfert de données
- **Mappers** : Conversion entre entités et DTOs (MapStruct)
- **Security** : Configuration de sécurité et JWT
- **Config** : Configurations diverses

## Tests

Les tests sont organisés en :
- Tests unitaires
- Tests d'intégration (avec Testcontainers)

Pour exécuter les tests :

```bash
./mvnw test
```

## Structure de la base de données

Les migrations de base de données sont gérées par Liquibase et se trouvent dans `src/main/resources/db/changelog/`.

## Déploiement
(May be)

## Références

- [RFC 7231 – HTTP/1.1 Semantics](https://tools.ietf.org/html/rfc7231)
- [Documentation Spring Boot](https://spring.io/projects/spring-boot)
- [OWASP API Security Top 10](https://owasp.org/www-project-api-security/)

## Évolutions futures

- Système de promotions avancé
- Gestion des retours
- Support multi-devises
- Support multi-langues

## Licence

MIT

## ****Auteur

alkaly02

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request.
