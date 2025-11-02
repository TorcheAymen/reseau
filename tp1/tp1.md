1) Notez l’adresse IPv4 associée à l’interface ethernet de votre ordinateur avec la commande
suivante : 
```bash
aymen.torche.etu@b05p20:~$ ip -brief address show
lo               UNKNOWN        127.0.0.1/8 ::1/128 
eth0             DOWN           
eth1             UP             192.168.5.70/24 metric 100 fe80::266a:eff:fe77:6493/64 
```

ainsi notre ipv4 est : 127.0.0.1/128 


à partir de ce moment on a lancé socklab via la commande : /home/share/applications/socklab/socklab 

2) Lancez la commande suivante pour créer un socket UDP : socket udp. En retour, socklab vous renvoie le descripteur du socket. Notez ce chiffre car il sera utilisé pour manipuler ce socket.

--> création d'un socket : 

    socklab> socket udp
    The socket identifier is 3

3) Lancez la commande suivante pour afficher des informations sur le socket que vous venez de
créer :
```bash
status ou = 

socklab> =
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
>3   UDP U   -                          -                          ipv4  .W.

socklab> status
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
>3   UDP U   -                          -                          ipv4  .W.
```


4) Utilisez la commande bind pour associer ce socket avec l’adresse IPv4 de votre ordinateur et avec
le port 3000 : bind <id_socket> <adresse_ip> 3000 

```bash
socklab> bind 4 192.168.5.70
Port? :  3000

socklab> status
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
 3   UDP U   127.0.0.1(3000)            -                          ipv4  .W.
>4   UDP U   192.168.5.70(3000)         -                          ipv4  .W.
```

--> On voit bien ici que l'adresse locale ainsi que le port ont été renseignés 

5) La commande pour envoyer un message depuis un socket UDP vers une autre est : sendto <id_socket> <ip_dst> <port_dst> <msg>

        id_socket : identifiant du socket local à utiliser pour envoyer le message
        ip_dst : adresse IP de destination du message
        ULille/FST/FIL - L3 S5 info/miage – X. Buche p. 1/7
        port_dst : port de destination du message
        msg : message à envoyer, au format ASCII et entre guillemets


Envoyez un gentil message vers l’ordinateur de votre voisin. 
```bash
    socklab> sendto 4 192.168.5.69 44703 salut   
    Sent 5 bytes

    socklab> 
```

6) Quelles informations votre voisin doit-il vous fournir pour que vous puissiez lui envoyer un
message ? 

Son adresse ipv4 ( eth1 ) ainsi que le port via lequel il va recevoir le message 

7) Une fois le message envoyé, il se trouve dans le buffer associé au socket de destination. Pour l’afficher, utilisez la commande : recvfrom <id_socket> <nb_octets>
    id_socket : identifiant du socket de destination du message
    nb_octets : nombre de caractères à lire dans le buffer


8) Selon vous, il est préférable de laisser le système choisir le port sur la machine qui envoie le
message initial, ou sur celui qui le reçoit, ou les deux ?

--> Il est préférable de laisser le système choisir le port sur la machine qui envoie le message initial. Celui qui reçoit doit être fixé.  ( voir principe client-serveur ) 

9) Supprimez le socket créé précédemment avec la commande :
close <id_socket>

```bash
socklab> =
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
 4   UDP U   192.168.5.70(3000)         -                          ipv4  .W.

socklab> close 4 

socklab> = 
No socket has been created yet.
```


10) Créez maintenant 2 sockets UDP et associez-les à toutes les adresses IP de votre machine et à un
port libre choisi par le système.

Vérifiez la configuration de vos sockets avec la commande « status ».

Les sockets ont étés crées sur deux laboratoires socklab différents 

Lab 1 : 
```bash 
socklab> socket udp
The socket identifier is 3

socklab> bind 3 * 0
The socket was attributed port 44796.

socklab> =
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
>3   UDP U   *(44796)  
```

Lab 2 : 

```bash

socklab> bind 3 * 0
The socket was attributed port 34954.

socklab> =
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
>3   UDP U   *(34954)    
```

11) Ouvrez un nouveau terminal et exécutez la commande suivante pour capturer la communication
sur l’interface de loopback : sudo wireshark -i lo -f udp

--> C'est fait et après un double clique sur loopback j'ai accès aux 3 onglets 

12) Ouvrez un autre terminal et lancez la commande « ip address » pour connaître l’adresse IPv4
associée à l’interface de loopback.

inet 127.0.0.1/8 scope host lo


15) 
    1.  Vérifiez que l’adresse IP source et le port source correspondent au socket sur lequel les messages ont été envoyés.
        Vérifiez que l’adresse IP de destination et le port de destination correspondent au socket sur lequel les messages ont été reçus.

    On a bien toutes les informations

    3. ip_src : 192.168.5.70 | ip_dest : 192.168.5.70  Message envoyé : Hello aymen
       port_src : 44796      | port_dest : 34954 

    4) Calculez l’efficacité du protocole, c’est-à-dire le rapport entre la taille des messages en octets et la taille totale des données transmises pour véhiculer ce message 
       (valeur indiquée dans la ligne « frame » ( ici 440 bits ) de chaque trame dans Wireshark)

        efficacité = taille totale du message ( des 3 frames ) / taille totale des 3 frames 

       ( bon j'ai repris le TD ici et donc je n'ai pas respecté à 100% mes anciens travaux ) 

       taille frame = 480 bits = 60
       taille data = 16 

       60/16 = 0,267

       donc l'efficacité est de 0.267


## PARTIE TCP 
Voici ton TP TCP réécrit en format **“mdr”** avec une section **Réponse** vide pour chaque question :

````markdown
# TP TCP – Socklab / Wireshark

## 1) Créez 2 sockets TCP associés à l’adresse IP de l’interface de loopback :
- S1 → port TCP 3000  
- S2 → port TCP 4000  

---

## 2) Capturez la communication sur l’interface loopback :
```bash
sudo wireshark -i lo -f 'tcp port 3000' &
````


## 3) Listez les sockets TCP ouverts :

```bash
ss -ant4

aymen.torche.etu@b05p20:~$ ss -ant4
State      Recv-Q  Send-Q   Local Address:Port      Peer Address:Port  Process  
LISTEN     0       999          127.0.0.1:31416          0.0.0.0:*              
LISTEN     0       511          127.0.0.1:6379           0.0.0.0:*              
LISTEN     0       5              0.0.0.0:44321          0.0.0.0:*              
LISTEN     0       128            0.0.0.0:44322          0.0.0.0:*              
LISTEN     0       128            0.0.0.0:44323          0.0.0.0:*              
LISTEN     0       1024           0.0.0.0:22             0.0.0.0:*              
LISTEN     0       32           127.0.0.1:11200          0.0.0.0:*              
LISTEN     0       1024     127.0.0.53%lo:53             0.0.0.0:*              
LISTEN     0       5            127.0.0.1:61209          0.0.0.0:*              
LISTEN     0       1024        127.0.0.54:53             0.0.0.0:*              
LISTEN     0       50           127.0.0.1:11300          0.0.0.0:*              
LISTEN     0       1024         127.0.0.1:631            0.0.0.0:*              
ESTAB      0       0            127.0.0.1:42010        127.0.0.1:11300          
ESTAB      0       0         192.168.5.70:55132   64.233.167.188:443            
ESTAB      0       0         192.168.5.70:40256   172.64.148.235:443            
TIME-WAIT  0       0         192.168.5.70:37822     172.18.12.22:3306           
ESTAB      0       0            127.0.0.1:6379         127.0.0.1:60920          
ESTAB      0       0            127.0.0.1:11300        127.0.0.1:42010          
ESTAB      0       0            127.0.0.1:60920        127.0.0.1:6379           
ESTAB      0       0         192.168.5.70:806        172.18.12.9:2049   
```


---

## 4) Établissez une connexion depuis S1 vers S2 :

```bash
connect <id_socket> <ip_dst> <port_dst>
```

Que constatez-vous ?

**Réponse :**
```bash
connect 3 192.168.5.70  4000
```

La connection a été refusée 
---

## 5) Mettez le socket S2 en écoute passive :

```bash
listen <id_socket> <nb_connexions>
```

**Réponse :**
```bash
socklab> listen 3 3 

socklab> connect 3 192.168.5.70  4000
Connection established.
```

Maintenant la connexion est établie 
---

## 6) Quel est le socket « serveur » et le socket « client » ?

**Réponse :**
Un socket client va envoyer une demande de connection et un serveur se met en mode écoute. 

S1 est donc le client et S2 le serveur

---

## 7) Tentez de nouveau d’établir une connexion depuis S1 vers S2.

Que constatez-vous au niveau de Wireshark ?

**Réponse :**
On voit les échanges SYN-ACK qui initialisent la connexion. 
---

## 8) Acceptez la connexion sur S2 :

```bash
accept <id_socket>
```

**Réponse :**

```bash
socklab> accept 3
A connection from b05p20.fil.univ-lille.fr (3000) was received.
Connection is established, with socket ID 4.
```
---

## 9) Lancez la commande `status`. Que constatez-vous ?

**Réponse :**

```bash
socklab> status
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
 3   TCP     *(4000)                    -                          ipv4  ...
>4   TCP     192.168.5.70(4000)         192.168.5.70(3000)         ipv4  .W.


```

---

## 10) Envoyez le message « Comment allez-vous ? » (20 caractères) depuis S1 vers S3 :

```bash
write <id_socket> "Comment allez-vous ?"
```

**Réponse :**

```bash
socklab> write 3 Comment Allez Vous
Sent 7 bytes
```


---

## 11) Analysez les paquets échangés sur Wireshark :

### 11-a) Que se serait-il passé si le flag PSH n’avait pas été activé par l’émetteur ?

**Réponse :**
On aurait pu ne pas recevoir le message immédiatement, il serait resté coincé dans le buffer .
### 11-b) À quoi correspond le numéro de séquence du segment envoyé ?

**Réponse :**
Il identifie la position du 1er octet su segment dans le flux TCP
    Sequence Number (raw): 2964850559

### 11-c) À quoi correspond le numéro d’acquittement du segment reçu ?

**Réponse :**

Il indique le prochain octet attendu par le récepteur, servant à confirmer la réception des octets précédents.

    Acknowledgment number (raw): 3123665529

### 11-d) Calculez la différence entre le numéro de séquence et le numéro d’acquittement. À quoi correspond ce résultat ?

**Réponse :**
Ack−Seq = 3123665529 − 2964850559 = 158815970

correspond au nombre d'octets reçus et acquités par le destinataire
---

## 12) Lancez de nouveau :

```bash
ss -ant4
```

Que contient le champ `Recv-Q` associé à la connexion ?

**Réponse :**

---

## 13) Lire le message :

```bash
read <id_socket> <nb_octets>
```

id_socket : socket destinataire du message
nb_octets : nombre d’octets à lire

**Réponse :**

---

## 14) Lancez de nouveau :

```bash
ss -ant4
```

Valeur du champ `Recv-Q` maintenant ?

**Réponse :**

---

## 15) Fermez la sortie du socket S1 :

```bash
shutdown <id_socket> out
```

Analysez avec Wireshark. Que se passe-t-il si vous essayez d’envoyer un 2ème message ?

**Réponse :**

---

## 16) Répondez au 1er message avec « Tres bien. Merci ! » (18 caractères)

Envoyez et lisez avec le bon socket.

**Réponse :**

---

## 17) Utilisez shutdown depuis le serveur vers le client

Analysez le fonctionnement.

**Réponse :**

---

## 18) Analysez la capture :

### 18-a) Schéma de la session de communication

**Réponse :**

### 18-b) Combien de segments TCP ont été transmis ? Comparez avec UDP

**Réponse :**

### 18-c) Calculez l’efficacité du protocole :

$$
\text{Efficacité} = \frac{\text{Taille du message (octets)}}{\text{Taille totale des données transmises (octets)}}
$$

Comparez avec UDP.

**Réponse :**

---

## 19) Fermez tous les sockets :

```bash
close <id_socket>
```

**Réponse :**












