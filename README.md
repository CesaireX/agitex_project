# agitex_project
projet pour un test d'essai à agitex

## Requirements
-Installer Java
-Installer Spring boot
-Installer Docker
-Installer Postman ou tout autre logiciel pour effectuer les tests d'API

## Installation
1. Clonez le référentiel Git.
2. run * mvn package -DskipTests * a la racine du projet pour creer le jar
2. Build et exécutez l'application Docker en utilisant la commande suivante :
             
            docker build . -t agitex:v1 ---Le build du projet
  
            docker-compose up ---L'execution du projet

## TESTS
 Pour la phase de test concernant la création des clients a travers les fichiers, certains fichiers ont été déja créer et integrer afin de permettre un test rapide sans pour autant etre obliger de saisir encore des fichiers d'essais
 Vous pouvez retrouver ces fichiers au niveau du folder: src/main/ressources/files/
 
 En guise de test 5 types de fichiers ont été Pris en compte:
 
 *Pour chaque partie je vous laisse l'url a saisir sur postman afin de tester
 
  1. Excel
  
  url: http://localhost:8081/excel (method: POST)
  Body: Key = "file" => type file
       value= select a file .xlsx (comme exemple un fichier Agitex.xlsx est disponible dans mon folder ci dessus notifié
  
  2. csv
  
  url: http://localhost:8081/csv (method: POST)
  Body: Key = "file" => type file
       value= select a file .xlsx (comme exemple un fichier Agitex.csv est disponible dans mon folder ci dessus notifié
       
  3. xml
  
  url: http://localhost:8081/xml (method: POST)
  Il y'a une petite nuance ici, car on ne charge pas le fichier mais plutot son path
  Body: Key = "file" => type text
       value= ecrire le path menant au fichier.xml (comme exemple un fichier Agitex.xml est disponible dans mon folder ci dessus notifié
  
  4. text
  
  url: http://localhost:8081/text (method: POST)
  Body: Key = "file" => type text
       value= ecrire le path menant au fichier.txt (comme exemple un fichier Agitex.txt est disponible dans mon folder ci dessus notifié
  
  5. json
  
  url: http://localhost:8081/json (method: POST)
  Body: Key = "file" => type file
       value= select a file .json (comme exemple un fichier Agitex.json est disponible dans mon folder ci dessus notifié
       
         Afin de verifier la veracité des données un service pour recuperer la liste des clients a été ajouter
  
  6. getallclients
  
  url: http://localhost:8081/client (method: GET)
  Body: null
  
         La derniere requete est celle correspondant à la moyenne de salaire en fonction de la profession
         
   7. getmoyenneofprofession
   
  url: http://localhost:8081/moyennebyprofession/{profession} (method: POST)
  Body: null
