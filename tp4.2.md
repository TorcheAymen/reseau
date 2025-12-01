# Adressage

#### Remarque : La partie Adressage de ce tp a été fait sur ma machine personnelle. Le reste se fera soit en salle de TP soit sur ma machine personnelle avec des outils de simulation de réseaux. 

1. Sur votre machine, quel est le nom de l’interface connectée au réseau de la salle de TP ?
   - Quelle est son adresse IPv4 ?
   - Quelle est son adresse IPv6 ?
   - Quelle est son adresse MAC ?

**Réponse :** 

```bash
3: eth1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc htb state UP group default qlen 1000
    link/ether 24:6a:0e:77:5e:1a brd ff:ff:ff:ff:ff:ff
    inet 192.168.5.71/24 brd 192.168.5.255 scope global eth1
       valid_lft forever preferred_lft forever
    inet6 fe80::266a:eff:fe77:5e1a/64 scope link
       valid_lft forever preferred_lft forever
```

   - adresse IPv4 : **192.168.5.71/24**
   - adresse IPv6 : **fe80::266a:eff:fe77:5e1a/64**
   - adresse MAC : **24:6a:0e:77:5e:1a**

2. Donnez les principales caractéristiques de cette interface :
   - Quelle est sa vitesse actuelle ? ( )
   - Est-ce la vitesse maximale supportée par l’interface ?
   - Quel le mode de duplex ?
   - Vérifiez que l’interface est bien connectée.

**réponse :** 
```bash
$ sudo ethtool eth1
Settings for eth1:
    Supported ports: [ TP ]
    Supported link modes:   10baseT/Half 10baseT/Full
                            100baseT/Half 100baseT/Full
                            1000baseT/Full
    Supported pause frame use: Symmetric Receive-only
    Supports auto-negotiation: Yes
    Advertised link modes:    10baseT/Half 10baseT/Full
                              100baseT/Half 100baseT/Full
                              1000baseT/Full
    Speed: 1000Mb/s
    Duplex: Full
    Auto-negotiation: on
    Port: Twisted Pair
    MDI-X: on (auto)
    Link detected: yes
```

   - vitesse actuelle : **1000 Mb/s**
   - vitesse maximale : **oui**
   - mode de duplex   : **Full**
   - interface bien connectée : **statut UP**

3. Quelle est la classe de l’adresse IPv4 ?
   - Quel est son masque ?
   - Quelle est l’adresse du réseau au format CIDR ?
   - Quelle est l’adresse de broadcast ?
   - Combien d’hôtes peut-on adresser sur ce réseau ?
   - Cette adresse est-elle routable au sein d’un réseau local ? Est-elle routable sur internet ?

catégories d'adresses IP. Il y'en a 5 (A, B, C, D, E) 
    - classe A : Le premier octet est entre 0 et 127 inclus
    - classe B : Le premier octet est entre 128 et 191 inclus
    - classe C : Le premier octet est entre 192 et 223 inclus 
    - classe D : Le premier octet est entre 224 et 239 inclus 
    - classe E : Le premier octet est entre 240 et 255 inclus 

**réponses :** 

Adresse : **192.168.5.71**

→ Premier octet = 192 → **Classe C**

- Adresse réseau : **192.168.5.0/24**
- Masque : **255.255.255.0**
- Broadcast : **192.168.5.255**
- Nombre d’hôtes :
  - 32 - 24 = 8 bits pour les hôtes
  - 2⁸ = 256 adresses
  - 256 - 2 = **254 hôtes**

Notion de routabilité :

- Cette adresse appartient à **192.168.0.0/16** → plage privée.
- Donc :
  - **routable dans le réseau local**
  - **non routable sur Internet**

4. Écrivez les 16 octets de l’adresse IPv6 sans abréviation. Écrivez en dessous les 16 octets du masque.
   - Combien d’hôtes peut-on adresser sur ce réseau ?
   - Cette adresse est-elle routable au sein d’un réseau local ? Est-elle routable sur internet ?
   - Quelle est l’étendue (scope) de cette adresse ?
   - Affichez la table de routage IPv4. Quelle est l’adresse IPv4 de la passerelle ?

**réponse :**

Adresse IPv6 : **fe80::266a:eff:fe77:5e1a**

Sans abréviation :

**fe80:0000:0000:0000:266a:0eff:fe77:5e1a**

Masque /64 :

**ffff:ffff:ffff:ffff:0000:0000:0000:0000**

Nombre d’hôtes :

- 128 − 64 = 64 bits
- 2⁶⁴ ≈ **1,8×10¹⁹ adresses**  ce qui est giganto-enormissime ( c'est bien plus que toutes les adresses ipv4 qui existent)

Scope : **link-local** → communication uniquement sur le lien local, non routable.

Routabilité :

- IPv6 fe80:: → **non routable**, seulement local-link

Passerelle IPv4 (via `ip route`) : dépend de la machine mais sur ce réseau TP :

→ généralement **192.168.5.254** (selon config de la salle)

5. Avec Wireshark…

- Avec l'IPV4 de mon voisin (192.178.) : 
```bash 
aymen.torche.etu@b05p21:~$ ping 192.168.5.69
PING 192.168.5.69 (192.168.5.69) 56(84) bytes of data.
64 bytes from 192.168.5.69: icmp_seq=1 ttl=64 time=0.535 ms
64 bytes from 192.168.5.69: icmp_seq=2 ttl=64 time=0.416 ms
64 bytes from 192.168.5.69: icmp_seq=3 ttl=64 time=0.358 ms
^C
--- 192.168.5.69 ping statistics ---
3 packets transmitted, 3 received, 0% packet loss, time 2056ms
rtt min/avg/max/mdev = 0.358/0.436/0.535/0.073 ms
``` 

- Avec son IPV6 : 
```bash
aymen.torche.etu@b05p21:~$ ping -6 fe80::266a:eff:fe77:5dc2%eth1
PING fe80::266a:eff:fe77:5dc2%eth1 (fe80::266a:eff:fe77:5dc2%eth1) 56 data bytes
64 bytes from fe80::266a:eff:fe77:5dc2%eth1: icmp_seq=1 ttl=64 time=1.10 ms
64 bytes from fe80::266a:eff:fe77:5dc2%eth1: icmp_seq=2 ttl=64 time=0.644 ms
64 bytes from fe80::266a:eff:fe77:5dc2%eth1: icmp_seq=3 ttl=64 time=0.472 ms
64 bytes from fe80::266a:eff:fe77:5dc2%eth1: icmp_seq=4 ttl=64 time=0.443 ms
```


IPV6 étant une adresse link-local (c-a-d qu'elle permet uniquement de communiquer avec son voisin directe (ne passe par aucun routeur), il faut préciser par QUELLE interface il faut faire passer les packets )


6–13. (inchangés)
