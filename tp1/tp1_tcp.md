## PARTIE TCP 

````markdown
# TP TCP – Socklab / Wireshark

## 1) Créez 2 sockets TCP associés à l’adresse IP de l’interface de loopback :

```bash 
socklab> =
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
 3   TCP     127.0.0.1(3000)            -                          ipv4  RW.
>4   TCP     127.0.0.1(4000)            -                          ipv4  RW.
```

---

## 2) Capturez la communication sur l’interface loopback :
```bash
sudo wireshark -i lo -f 'tcp port 3000' &
````


## 3) Listez les sockets TCP ouverts :

```bash
ss -ant4

┌──(kali㉿kali)-[~]
└─$ ss -ant4
State    Recv-Q    Send-Q       Local Address:Port       Peer Address:Port   
UNCONN   0         0                127.0.0.1:3000            0.0.0.0:*      
UNCONN   0         0                127.0.0.1:4000            0.0.0.0:*      
                                                                             
┌──(kali㉿kali)-[~]
└─$ 


```


---

## 4) Établissez une connexion depuis S1 vers S2 :

```bash
connect <id_socket> <ip_dst> <port_dst>
```

Que constatez-vous ?

**Réponse :**
```bash
socklab> connect 3 127.0.0.1 4000
connect(): Connection refused
``` 

### On remarque sur WireShark une poignée de main propre au protocole TCP : Envoi d'un flag SYN et reception d'un flag RST-ACK : Le serveur a bien reçu la demande de connexion, mais celle-ci a été refusée ( d'où la présence du flag ACK)

![Refus de connexion TCP](refustcp.png)

---

## 5) Mettez le socket S2 en écoute passive :

```bash
listen <id_socket> <nb_connexions>
```

**Réponse :**
```bash
socklab> listen 4 8

socklab> connect 3 127.0.0.1 4000
Connection established.

Maintenant la connexion est établie 
``` 

![Autorisation connexion TCP](autortcp.png)

### On remarque l'échange SYN SYN-ACK ACK propre au TCP

---

## 6) Quel est le socket « serveur » et le socket « client » ?

**Réponse :**
Un socket client va envoyer une demande de connection et un serveur se met en mode écoute. 

S1 (3) est donc le client et S2 (4) le serveur

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

socklab> accept 4
A connection from localhost (3000) was received.
Connection is established, with socket ID 5.

socklab> 

```
---

## 9) Lancez la commande `status`. Que constatez-vous ?

**Réponse :**

```bash
socklab> =
 Id  Proto   Local address              Remote address             TYPE  RWX ?
 ---------------------------------------------------------------------------
 3   TCP     127.0.0.1(3000)            127.0.0.1(4000)            ipv4  .W.
 4   TCP     127.0.0.1(4000)            -                          ipv4  ...
>5   TCP     127.0.0.1(4000)            127.0.0.1(3000)            ipv4  .W.

```

On constate qu'un nouveau socket a été crée, c'est lui qui joue le rôle de serveur maintenant

---

## 10) Envoyez le message « Comment allez-vous ? » (20 caractères) depuis S1 vers S3 :

```bash
write <id_socket> "Comment allez-vous ?"
```

**Réponse :**

```bash
socklab> write 3 "comment allez-vous ?"
Sent 20 bytes


```


---

## 11) Analysez les paquets échangés sur Wireshark :

### 11-a) Que se serait-il passé si le flag PSH n’avait pas été activé par l’émetteur ?

**Réponse :**
On aurait pu ne pas recevoir le message immédiatement, il serait resté coincé dans le buffer . On aurait du remplir le buffer pour recevoir le message avec read 
### 11-b) À quoi correspond le numéro de séquence du segment envoyé ?

**Réponse :**
Seq du segment envoyé= 1 (c'est la position du premier octet de la chaîne "Comment allez-vous ?", il s'agit entre autres de la position du premier octets des données utiles. Comme notre donnée mesure 20 octets, la prochaine donnée utile sera à la position 20 + 1 = 21 ).

### 11-c) À quoi correspond le numéro d’acquittement du segment reçu ?

**Réponse :**

Il porte le numéro 21, qui comme on l'a dit correspond à la position à laquelle sera la prochaine donnée utile. Entre autre, il confirme la récéption des 20 octets, et indique où positionner les prochains à recevoir

### 11-d) Calculez la différence entre le numéro de séquence et le numéro d’acquittement. À quoi correspond ce résultat ?

**Réponse :**
Ack−Seq = 21 − 1 = 20

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
```bash
┌──(kali㉿kali)-[~]
└─$ ss -ant4

State    Recv-Q    Send-Q       Local Address:Port       Peer Address:Port   
LISTEN   0         8                127.0.0.1:4000            0.0.0.0:*      
ESTAB    0         0                127.0.0.1:3000          127.0.0.1:4000   
ESTAB    20        0                127.0.0.1:4000          127.0.0.1:3000 
```
---

## 15) Fermez la sortie du socket S1 :

```bash
shutdown <id_socket> out
```

Analysez avec Wireshark. Que se passe-t-il si vous essayez d’envoyer un 2ème message ?

**Réponse :**
Le client S1 envoie un flag FIN à la position 21. Le server S2 renvoie un flag ACK à la position 22. J'en déduit que la commande shutdown met fin à la connexion du client S1 vers le server S2 mais pas l'inverse. S2 peut encore lui répondre.

Si on envoie un 2nd message ça ne marchera pas ! Aucun signal ne sera envoyé (aucune capture WireShark)
---

## 16) Répondez au 1er message avec « Tres bien. Merci ! » (18 caractères)

Envoyez et lisez avec le bon socket.

**Réponse :**

```bash
socklab> write 5 "Très bien Merci !"
Sent 18 bytes

```

---

## 17) Utilisez shutdown depuis le serveur vers le client

Analysez le fonctionnement.

**Réponse :**
On retrouve le même fonctionnement que lorsque l'on a arrêté le client FIN et ACK
---

## 18) Analysez la capture :

### 18-a) Schéma de la session de communication

**Réponse :**
 Sur feuille 

### 18-b) Combien de segments TCP ont été transmis ? Comparez avec UDP

**Réponse :**
13 segments ont étés envoyés contre 2 via UDP

### 18-c) Calculez l’efficacité du protocole :


\text{Efficacité} = \frac{\text{Taille du message (octets)}}{\text{Taille totale des données transmises (octets)}}

Efficacité = Taille du message / taille totale des données transmises 

D'abord regardons les tailles des trames : 
| Segment N° | Rôle | Longueur du segment (Octets) |
| :---: | :--- | :---: |
| 5 | SYN (établissement) | 74 |
| 6 | SYN/ACK (établissement) | 66 |
| 7 | ACK (établissement) | 66 |
| 8 | PSH (Donnée 20 octets) | 86 |
| 9 | ACK (réception donnée) | 66 |
| 10 | PSH (Donnée 18 octets) | 84 |
| 11 | ACK (réception donnée) | 66 |
| 12 | FIN (fermeture S1) | 66 |
| 13 | ACK (réception FIN) | 66 |
| 14 | FIN (fermeture S2) | 66 |
| 15 | ACK (réception FIN) | 66 |
| **Total** | 11 segments de la connexion réussie | **780** |

Taille du message = 18+20 = 38

Efficacité = 38 / 780 = 0.04871794871
Comparez avec UDP.
efficacité est de 0.267 pour UDP




**Réponse :**

On a **4%** d'efficacité pour TCP contre **26%** avec UDP. 
L'un est plus efficace et l'autre plus sécurisé


---











