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

Le projet adopte une **architecture orientée services (API REST)** organisée par **domaine/acteur** avec le principe de **single responsibility**, permettant une intégration avec plusieurs clients (Web, Mobile, etc.) et garantissant une meilleure maintenabilité et testabilité.

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

## Qualité et Tests

Ce projet met l'accent sur la **qualité du code** et les **bonnes pratiques de développement** :

### Test-Driven Development (TDD)
Le projet suit une approche **TDD (Test-Driven Development)** : les tests sont écrits avant ou en parallèle du code de production, garantissant une couverture de code élevée et une meilleure conception des fonctionnalités.

### Tests d'intégration avec Testcontainers
Les **tests d'intégration** utilisent **Testcontainers** pour créer des environnements de test isolés et réalistes :
- **PostgreSQL en conteneur Docker** : Chaque test d'intégration s'exécute avec une base de données PostgreSQL réelle dans un conteneur Docker
- **Tests end-to-end** : Les tests couvrent l'ensemble de la stack (Controller → Service → Repository → Base de données)
- **Isolation complète** : Chaque test dispose de son propre environnement, garantissant la reproductibilité et l'indépendance des tests
- **Tests d'API REST** : Utilisation de MockMvc et RestAssured pour tester les endpoints HTTP

### Stratégie de tests
- **Tests unitaires** : Validation de la logique métier isolée
- **Tests d'intégration** : Validation du comportement end-to-end avec base de données réelle
- **Tests de sécurité** : Validation de l'authentification JWT et de l'autorisation par rôles
- **Couverture de code** : Objectif de maintenir une couverture élevée pour garantir la fiabilité

Cette approche rigoureuse garantit la **fiabilité**, la **maintenabilité** et la **qualité** du code produit.

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

Les fonctionnalités sont organisées par domaine (acteur) pour respecter le principe de single responsibility.

### Authentification (Domaine partagé)
- Inscription des utilisateurs
- Connexion avec email et mot de passe
- Génération de token JWT
- Gestion des rôles (CLIENT, ADMIN, LIVREUR)

### Domaine Client
- **Catalogue** : Consultation du catalogue de produits
- **Panier** : Ajout, modification de quantité, suppression de produits
- **Commandes** : Validation de commandes, calcul automatique du montant total, consultation de l'historique
- **Paiement** : ⚠️ Simulation de paiement (service externe non intégré), enregistrement du statut

### Domaine Admin
- **Produits** : CRUD complet sur les produits
- **Catégories** : Gestion des catégories de produits
- **Utilisateurs** : Consultation de la liste, activation/désactivation
- **Livraisons** : Attribution d'une commande à un livreur

### Domaine Livreur
- **Livraisons** : Mise à jour du statut de livraison

## Exigences non fonctionnelles

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
- Architecture claire organisée par domaine (single responsibility)
- Code respectant les bonnes pratiques (SOLID)
- Séparation des responsabilités par acteur métier

## Architecture

Le projet adopte une **architecture orientée single responsibility** et organisée **par domaine (acteur)**. Chaque domaine encapsule ses propres responsabilités, garantissant une séparation claire des préoccupations et une meilleure maintenabilité.

### Principes architecturaux

- **Single Responsibility Principle (SRP)** : Chaque service a une responsabilité unique et bien définie
- **Organisation par domaine/acteur** : Le code est structuré selon les acteurs du système (Client, Admin, Livreur)
- **Séparation des couches** : Controllers, Services, Repositories, DTOs et Mappers sont clairement séparés
- **Interfaces pour les services** : Chaque service expose une interface (I*Service) garantissant l'abstraction et la testabilité

### Structure par domaine

```
src/main/java/com/e_com/e_com_spring/
├── controller/          # Points d'entrée de l'API REST organisés par domaine
│   ├── admin/          # Controllers pour les opérations administratives
│   └── auth/           # Controller d'authentification (partagé)
│
├── service/            # Logique métier organisée par domaine
│   ├── admin/          # Services administratifs
│   │   ├── category/   # Gestion des catégories
│   │   ├── delivery/   # Gestion des livraisons (vue admin)
│   │   ├── product/    # Gestion des produits
│   │   └── user/       # Gestion des utilisateurs
│   │
│   ├── auth/           # Service d'authentification (partagé)
│   │
│   ├── client/         # Services pour les clients
│   │   ├── cart/       # Gestion du panier
│   │   ├── catalog/    # Consultation du catalogue
│   │   ├── order/      # Gestion des commandes
│   │   └── payment/    # Gestion des paiements (simulés)
│   │
│   └── deliveryPerson/ # Services pour les livreurs
│       └── delivery/   # Gestion des livraisons (vue livreur)
│
├── dto/                # Objets de transfert de données organisés par domaine
│   ├── auth/           # DTOs d'authentification
│   └── user/           # DTOs utilisateur
│
├── mapper/             # Mappers MapStruct pour conversion Entité ↔ DTO
│
├── model/              # Entités JPA et modèles partagés
│   ├── auditing/       # Infrastructure d'audit
│   ├── Privilege.java
│   ├── Role.java
│   ├── RoleType.java
│   └── User.java
│
├── security/           # Configuration de sécurité et JWT
│
└── config/             # Configurations diverses
    └── auditing/       # Configuration de l'audit
```

### Domaines fonctionnels

#### 🔐 Domaine Auth (Partagé)
- **Service** : `service/auth/IAuthService`
- **Controller** : `controller/auth/AuthenticationController`
- **Responsabilité** : Gestion de l'authentification et de l'autorisation (inscription, connexion, génération JWT)

#### 👤 Domaine Client
- **Services** :
  - `service/client/cart/ICartService` : Gestion du panier
  - `service/client/catalog/ICatalogService` : Consultation du catalogue
  - `service/client/order/IOrderService` : Gestion des commandes
  - `service/client/payment/IPaymentService` : Simulation de paiement
- **Responsabilités** : Toutes les fonctionnalités dédiées aux clients (consultation, achat, commande)

#### 👨‍💼 Domaine Admin
- **Services** :
  - `service/admin/product/IProductService` : CRUD produits
  - `service/admin/category/ICategoryService` : Gestion des catégories
  - `service/admin/user/IUserService` : Gestion des utilisateurs
  - `service/admin/delivery/IDeliveryService` : Attribution des livraisons
- **Responsabilités** : Administration complète de la plateforme

#### 🚚 Domaine DeliveryPerson (Livreur)
- **Service** : `service/deliveryPerson/IDeliveryService`
- **Responsabilité** : Mise à jour du statut des livraisons

### Avantages de cette architecture

**Maintenabilité** : Chaque domaine est indépendant, facilitant les modifications
**Testabilité** : Services isolés et interfaces claires
**Scalabilité** : Facilite l'ajout de nouvelles fonctionnalités par domaine
**Clarté** : Structure intuitive reflétant les acteurs métier
**Réutilisabilité** : Services réutilisables au sein d'un même domaine
**Séparation des responsabilités** : Chaque service a un rôle précis et limité

## Tests

### Approche Test-Driven Development (TDD)

Ce projet applique rigoureusement les principes du **TDD (Test-Driven Development)** :
- **Écriture des tests avant le code** : Les spécifications sont d'abord traduites en tests
- **Refactoring continu** : Le code est amélioré en permanence tout en maintenant les tests verts
- **Documentation vivante** : Les tests servent de documentation exécutable du comportement attendu
- **Confiance dans le refactoring** : La suite de tests complète permet de modifier le code en toute sécurité

### Tests d'intégration avec Testcontainers

Les **tests d'intégration** sont au cœur de la stratégie de qualité du projet :

#### Infrastructure de test
- **Testcontainers** : Utilisation de conteneurs Docker pour créer des environnements de test réalistes
- **PostgreSQL en conteneur** : Chaque test d'intégration s'exécute avec une instance PostgreSQL isolée
- **Spring Boot Test** : Configuration complète du contexte Spring pour les tests end-to-end
- **MockMvc & RestAssured** : Tests des endpoints REST avec validation complète des réponses

#### Avantages
- **Environnements isolés** : Chaque test dispose de sa propre base de données
- **Tests reproductibles** : Même environnement à chaque exécution
- **Tests réalistes** : Utilisation d'une vraie base de données PostgreSQL
- **Pas de pollution** : Les tests ne s'affectent pas mutuellement
- **CI/CD ready** : Les tests peuvent s'exécuter dans n'importe quel environnement

#### Exemples de tests d'intégration
- **Tests d'authentification** : Inscription, connexion, génération de JWT
- **Tests de sécurité** : Validation des rôles et permissions
- **Tests de services** : Validation de la logique métier avec persistance réelle

### Organisation des tests

```
src/test/java/
├── controller/          # Tests d'intégration des endpoints REST
├── service/            # Tests unitaires et d'intégration des services
└── TestcontainersConfiguration.java  # Configuration des conteneurs de test
```

### Exécution des tests

Pour exécuter tous les tests (unitaires + intégration) :

```bash
./mvnw test
```

Pour exécuter uniquement les tests d'intégration :

```bash
./mvnw test -Dtest=*IntegrationTest
```

### Métriques de qualité

- **Couverture de code** : Objectif de maintenir une couverture élevée
- **Tests rapides** : Optimisation pour une exécution rapide des tests unitaires
- **Tests robustes** : Tests d'intégration complets pour valider le comportement réel

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

## Auteur

alkaly02

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request.
