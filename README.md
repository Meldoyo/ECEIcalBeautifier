#ECE Ical Beautifier

Programme visant à améliorer le calendrier offert par Hyperplanning de l'ECE Paris

##Utilisation

Télécharger la dernière version [ici](https://github.com/Meldoyo/ECEIcalBeautifier/releases)

```java -jar ECEIcal.jar <URLDuFluxIcal> output.ics```

Le code est à exécuter sur un serveur avec un refraîchissement régulier avec un accès web, genre dans un crontab sur un raspberry/machine de linux de l'école qui sont pas censés s'éteindre.


##Amélioration

L'idéal serait d'ajouter des paramètres de personnalisation plus poussé, pour l'instant ça marche comme moi je le veux (étudiant ING5), le code est pas compliqué, modifiez le si ça vous amuse.

###License

Ce code utilise [biweekly](https://github.com/mangstadt/biweekly/) pour la lecture du flux ical et [OkHttp](https://github.com/square/okhttp) pour le client HTTP