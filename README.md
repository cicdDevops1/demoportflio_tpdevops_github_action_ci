#  DevOps demoportflio_tpdevops_github_action_ci 

## Description

Ce projet est une API Spring Boot intégrant un pipeline CI/CD complet avec GitHub Actions et Docker.

Il permet de démontrer :
- Build Maven automatisé
- Création d’image Docker
- Push automatique vers Docker Hub
- Déploiement conditionnel sur la branche main

---

##  Technologies utilisées

- Java 17
- Spring Boot
- Maven
- Docker
- GitHub Actions
- Docker Hub

---

##  Architecture CI/CD

1. Push sur une branche dev ou main
2. Build Maven
3. Génération du JAR avec un artifact
4. Build Docker Image
5. Push vers Docker Hub
6. Déploiement (main uniquement)

---

## Installation locale

### 1️.Cloner le projet

```bash
git clone https://github.com/cicdDevops1/demoportflio_tpdevops_github_action_ci
cd demoportflio_tpdevops_github_action_ci

### 2️. Build Maven

mvn clean package

### 2️. Build Maven

java -jar target/*.jar

### 3️ Lancer l'application

java -jar app.jar

Docker
Build image

docker build -t devops-lab-01-ci

Run container

docker run -p 8080:8080 devops-lab-01-ci

CI/CD

Le pipeline GitHub Actions :

Se déclenche sur toutes les branches

Push l’image Docker avec un tag dynamique

Tag "latest" uniquement pour main

Déploie uniquement depuis main

Images


![alt text](images/image-1.png)
