#  DevOps demoportflio_tpdevops_github_action_ci 

## Description

Ce projet est une API Spring Boot intégrant un pipeline CI/CD complet avec GitHub Actions, Docker, et SonarQube pour l’analyse qualité et sécurité du code.

Le pipeline automatise :

 Build Maven

 Gestion des artefacts

 Analyse qualité et sécurité

 Création d’image Docker

 Push vers Docker Hub

Déploiement conditionnel

 ## Architecture CI/CD

Le pipeline suit un workflow DevOps moderne :

### Build Stage

Checkout du code source

Configuration Java 17

Cache Maven

Build du projet
### Quality & Security Stage (SonarQube)

Analyse statique du code

Détection des vulnérabilités

Vérification des bonnes pratiques

### Packaging Stage

Génération du fichier JAR

Sauvegarde en artifact GitHub

### Docker Stage

Construction de l’image Docker

Tag dynamique basé sur :

Branche

Pull Request

Push automatique vers Docker Hub

### Deployment Stage

Déploiement uniquement depuis la branche main

## Technologies utilisées

Java jdk17

Spring Boot 3.5.3

Maven

Docker

GitHub Actions

SonarQube

Docker Hub

## Installation et exécution locale
### Cloner le projet
git clone https://github.com/cicdDevops1/demoportflio_tpdevops_github_action_ci
cd demoportflio_tpdevops_github_action_ci
### Build le projet
mvn clean package
### Exécuter l’application
java -jar target/*.jar
### Docker
Build image Docker
docker build -t devops-lab-01-ci .
### Lancer le container
docker run -p 8080:8080 devops-lab-01-ci
## Sécurité & Qualité (SonarQube)

Le pipeline peut intégrer SonarQube :

Variables à configurer dans GitHub Secrets :

SONAR_HOST_URL

SONAR_TOKEN

Commande Maven :

mvn sonar:sonar
## Workflow CI/CD GitHub Actions

Le pipeline :

Se déclenche sur toutes les branches

Build automatiquement le projet

Analyse qualité et sécurité

Génère une image Docker

Push vers Docker Hub

Déploie uniquement depuis main

## Structure du projet
![alt text](https://github.com/devops-masterlab/demoportflio_tpdevops_github_action_ci/blob/main/images/image-2.png)
## Améliorations futures

✔ Tests unitaires 
✔ Déploiement Cloud
✔ Scan sécurité avancé

## Auteur

Projet DevOps Portfolio — CI/CD Spring Boot
Développé pour démonstration DevOps moderne.

💡 Contribution

Fork le projet

Créer une branche

Commit les modifications

Push et créer une Pull Request

Images de pipline


![alt text](https://github.com/devops-masterlab/demoportflio_tpdevops_github_action_ci/blob/main/images/image-1.png)
