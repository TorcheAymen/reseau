## Rendu du TP3 

### Exercice 1 


#### Q1 : quelles sont les étapes de traitement d'une requête 

Les étapes de traitement d'une requête TCP : 

TCP -> Triple poingée de main 

1. SYN : tentative de connexion 
2. SYN - ACK : tentative de connexion et acquittement
3. ACK : acquittement 


#### Q2 :  Quelles sont les exceptions à traiter pour chaque étape de votre programme ? S'assurer que chacune est bien affiché à l'administrateur du serveur.

Etape 1 : Connexion entre Serveur et client 

    - Client envoie un SYN au serveur

    - Serveur envoie SYN - ACK au client

    - Client envoie ACK au serveur 


Etape 2 : Affichage du message 

« Bienvenue sur mon serveur et au revoir » 

Etape 3 : Rupture de la connexion 



Implémentation : 

Pour le serveur : Java ServerSocket Class



#### Q3. Une fois le programme réalisé, comment en tester son bon fonctionnement ?

**réponse :** Il faut faire un client et le connecter

#### Q4. S'assurer que le programme fonctionne en boucle, c'est-a-dire, qu'il traite plusieurs requêtes les unes aprèsles autres. Ce faisant, le serveur peut recevoir une succession de requêtes de différents clients : comment garder la trace toutes les connections ayant eu lieu ? 

**réponse :**  Il faut conserver les Adresses des clients dans une structure de données 


**Cient :**

aymen.torche.etu@b05p21:~/Desktop/rsc$ java Client 
Le serveur a dit : Bienvenu sur mon serveur et au revoir 

**Server :** 

aymen.torche.etu@b05p21:~/Desktop/rsc$ java Server 
Starting server...
server started !
Un nouveau client a été accepté !







### Exercice 2 : 

#### Q1. Comment et quand créer un nouveau thread pour un client dans un programme 

**réponse :** A chaque fois qu'on accepte un client, il faut faire un nouveau thread pour attendre d'autres messages 

#### Q2. Quelles sont les primitives permettant de recevoir des chaines de caractères sur une Socket ?

1. s.getInputStream() : récupère le flux de données qui arrive du réseau  --> OCTETS 
2. objet d'instance InputStreamReader qui transforme les octets en caractères --> CARACTERES 
3. BufferReader : tampon stockant les données lues  (sous forme de caractères)
4. readLine() : méthode clé qui permet de récupérer sous forme d'une chaîne de caractères l'objet string -> String : message Final

### Q3. Comment faire pour retransmettre ces chaines vers tous les utilisateurs connectés ? Comment partager en Java, au niveau du constructeur de chaque Thread, une structure globale visible et mise à jour par tous les Threads 

**réponse :**  Il faut utiliser une liste de socket, qu'on va donner au thread pour qu'il puisse broadcaster le message. 



### Q4. S'assurer que le programme fonctionne en boucle, c'est-a-dire, qu'il traite plusieurs requêtes les unes après les autres. Ce faisant, le serveur peut recevoir une succession de requêtes de différents clients : comment garder la trace toutes les connections ayant eu lieu ?


**réponse :**  Il faut que les threads puissent accéder à une structure de données partagée pour ajouter les sockets qui se connectent et ainsi garder une trace 


##### résultat finale : 

**terminal 1 :** 

```bash 
aymen.torche.etu@b05p21:~/Desktop/rsc/tp3/exo2$ telnet localhost 2004
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
salut
comment tu t'appel
je m'appel bob
et toi ? 
```

**terminal 2 :**


```bash

aymen.torche.etu@b05p21:~/Desktop/rsc/tp3/exo2$ telnet localhost 2004 
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
salut
comment tu t'appel
je m'appel bob
et toi ? 

```

**côté serveur :** 

```bash
aymen.torche.etu@b05p21:~/Desktop/rsc/tp3/exo2$ java Server
Starting server...
server started !
Un nouveau client a été accepté !
Un nouveau client a été accepté !
```

#### Q5. Lorsqu'un client telnet quitte normalement (CTRL-D), ou alors est intempestivement arrêté, comment s'assurer du bon fonctionnement de l'application.

**réponse :** C'est cette partie de code dans la fonction run de ClientThread qui fait l'affaire ! 

```Java
this.listeClients.remove(this.s);
this.s.close();
System.out.println("Client déconnecté et retiré de la liste.");
```