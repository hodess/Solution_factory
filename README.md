# Locomotive

## Description du projet

Notre projet "Locomotive" vise à offrir aux utilisateurs un moyen optimal de se déplacer entre les gares en région parisienne, en prenant en compte les métros et les RER, sans collecter leurs données personnelles. En favorisant des trajets optimisés, nous contribuons à réduire l'empreinte carbone des utilisateurs. Accessible via une application web, Locomotive se distingue par son interface intuitive et ergonomique, garantissant une expérience utilisateur fluide et agréable.

## Pré-requis

Ce qui est requis pour commencer avec votre projet...

- Visual Studio Code
- MySQL Workbench
- IntelliJ (optionnel)

## Installation

### Ouvrir le projet avec Visual Studio Code

#### Back-end

1. Aller au fichier `back_end/back_end/src/main/resources/application.properties`
2. Changer la ligne `spring.datasource.password=` et mettre votre mot de passe pour accéder à votre base de données

**Option 1 :**

1. Ouvrir un terminal
2. Vérifier que vous avez l'extension Java + Spring Boot
3. Exécuter le fichier `Application.java` qui se trouve dans `back_end/back_end/src/main/java/org/example/Application.java`

**Option 2 avec IntelliJ :**

1. Ouvrir le fichier `back_end/back_end/`
2. Exécuter le fichier `src/main/java/org/example/Application.java`

#### Front-end

1. Ouvrir un terminal
2. Exécutez la commande `cd .\front_end\vue-demo\vue-demo\`
3. Vérifier que vous avez l'extension Vue.js
4. Ensuite, exécutez les commandes :
   - `npm install`
   - `npm run dev`

#### Base de données

1. Ouvrir le fichier `BDD`
2. Exécuter le fichier `locomotive` sur MySQL

#### Final

Votre site web est sur l'adresse IP : [http://localhost:5173/](http://localhost:5173/)  
Et votre API est sur le site web : [http://localhost:8081/hello](http://localhost:8081/hello)

## Auteurs

Liste des auteurs du projet :

- **Romain Dupont**
- **Eugénie Nakouzi**
- **Max Tonnadre**
- **Mattéo Buffey**
- **Théotime Huybrechts**
