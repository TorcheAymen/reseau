# Adressage

#### Remarque : La partie Adressage de ce tp a été fait sur ma machine personnelle. Le reste se fera soit en salle de TP soit sur ma machine personnelle avec des outils de simulaton de réseaux. 


1. Sur votre machine, quel est le nom de l’interface connectée au réseau de la salle de TP ?
   - Quelle est son adresse IPv4 ?
   - Quelle est son adresse IPv6 ?
   - Quelle est son adresse MAC ?

**Réponse :** 

```bash
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UP group default qlen 1000
    link/ether 08:00:27:8c:e4:fc brd ff:ff:ff:ff:ff:ff
    inet 192.168.1.50/24 brd 192.168.1.255 scope global dynamic noprefixroute eth0
       valid_lft 86372sec preferred_lft 86372sec
    inet6 2a01:cb0c:447:ea00:4c55:d889:36e0:8604/64 scope global dynamic noprefixroute 
       valid_lft 86372sec preferred_lft 572sec
    inet6 fe80::81e2:7d08:2e16:27e3/64 scope link noprefixroute 
       valid_lft forever preferred_lft forever

```

   - adresse IPv4 : 192.168.1.50/24 
   - adresse IPv6 :2a01:cb0c:447:ea00:4c55:d889:36e0:8604/64 
   - adresse MAC : 08:00:27:8c:e4:fc 

2. Donnez les principales caractéristiques de cette interface :
   - Quelle est sa vitesse actuelle ? ( )
   - Est-ce la vitesse maximale supportée par l’interface ?
   - Quel le mode de duplex ?
   - Vérifiez que l’interface est bien connectée.

**réponse :** 
```bash    
└─$ sudo ethtool eth0                       
    [sudo] password for kali: 
    Settings for eth0:
            Supported ports: [ TP ]
            Supported link modes:   10baseT/Half 10baseT/Full
                                    100baseT/Half 100baseT/Full
                                    1000baseT/Full
            Supported pause frame use: No
            Supports auto-negotiation: Yes
            Supported FEC modes: Not reported
            Advertised link modes:  10baseT/Half 10baseT/Full
                                    100baseT/Half 100baseT/Full
                                    1000baseT/Full
            Advertised pause frame use: No
            Advertised auto-negotiation: Yes
            Advertised FEC modes: Not reported
            Speed: 1000Mb/s
            Duplex: Full
            Auto-negotiation: on
            Port: Twisted Pair
            PHYAD: 0
            Transceiver: internal
            MDI-X: off (auto)
            Supports Wake-on: umbg
            Wake-on: d
            Current message level: 0x00000007 (7)
                                drv probe link
            Link detected: yes 
            
```

   - vitesse actuelle : Speed: 1000Mb/s
   - vitesse maximale : oui 
   - mode de duplex   : Duplex: Full
   - interface bien connecté : statut UP



3. Quelle est la classe de l’adresse IPv4 ?
   - Quel est son masque ?
   - Quelle est l’adresse du réseau au format CIDR ?
   - Quelle est l’adresse de broadcast ?
   - Combien d’hôtes peut-on adresser sur ce réseau ?
   - Cette adresse est-elle routable au sein d’un réseau local ? Est-elle routable sur internet ?

   **réponses :** 
   
   C'est quoi une classe ? 
   Ce sont des catégories d'adresses IP. Il y'en a 5 (A, B, C, D, E) 
    - classe A : Le premier octet est entre 0 et 127 inclus
    - classe B : Le premier octet est entre 128 et 191 inclus
    - classe C : Le premier octet est entre 192 et 223 inclus 
    - classe D : Le premier octet est entre 224 et 239 inclus 
    - classe E : Le premier octet est entre 240 et 255 inclus 

   Comment déterminer la classe d'une adresse IPv4 ? 

   Il faut regarder le premier octet de l'adresse en question, ici notre adresse c'est : 192.168.1.50. 
   Le premier octet est 192, ce qui correspond à une **adresse IP de classe C.**  

   c'est quoi l'@ de broadcast : 192.168.1.255. L'adresse qui permet de diffuser des données dans tout le sous-reseau

  adresse avec masque : 192.168.1.50/24 
  32 - 24 = 8 
  2**8 = 256 
  256 - 1 adresse broadcast - 1  adresse reseau = 254 

  On peut adresser 254 hôtes sur ce reseau


  Notion d'adresse routable : 

  Une adresse est routable si elle est présente dans les tables de routages et que les routeurs acceptent de la relayer. 

  Dans un réseau privée : 
  
  Les adresses d'un réseau interne sont routable dans ce même réseau mais non-routables sur internet. 

  Il existe 3 types d'adresses qui ne sont pas routables : 
   1. Les adresses de loopback : qui sont propres à la carte réseau et donc inconnue des routeurs 
   2. Les adresses de lien local : elles acceptent uniquement de communiquer avec leurs voisins ( IPV4 : 169.254.x.x et IPV6 : fe80::...)
   3. L'adresse de broadcast globale 255.255.255.255 car elle innonderait les réseaux du monde. 

DONC : oui cette adresse est routable dans son réseau local, et non routable sur internet. 


4. Écrivez les 16 octets de l’adresse IPv6 sans abréviation. Écrivez en dessous les 16 octets du masque.
   - Combien d’hôtes peut-on adresser sur ce réseau ?
   - Cette adresse est-elle routable au sein d’un réseau local ? Est-elle routable sur internet ?
   - Quelle est l’étendue (scope) de cette adresse ?
   - Affichez la table de routage IPv4. Quelle est l’adresse IPv4 de la passerelle ?
   

   **réponse :** 
   Adresse IPv4 sans abréviation : 2a01:cb0c:0447:ea00:4c55:d889:36e0:8604 -> on a rajouté les 0 manquants 

   Les 16 octets du masque :  Le masque est de 64, ça veut dire que les 64 premiers bits sont à 1 ce qui donne 

                        ffff:ffff:ffff:ffff:0000:0000:0000:000

   Combien d'hôtes peut-on adresser sur ce réseau ? 

   128 - masque = nombre d'octets restants 

   nb_adresse = 2**(nombre d'octets restants)

   soit 

   128-64 = 64 

   nb adresses = 2**64 ce qui est giganto-enormissime ( c'est bien plus que toutes les adresses ipv4 qui existent)


5. Avec Wireshark, lancez une capture de trames sur l’interface connectée au réseau de la salle de TP. Testez la connectivité IPv4 et IPv6 avec votre voisin.
   - **Note :** L’adresse IPv6 étant une adresse de lien local (elle commence par FE80), vous devez spécifier à la commande ping le nom de l’interface ethernet à utiliser pour envoyer la requête ICMP.
   - Expliquez pourquoi en vous aidant de la table de routage IPv6.

6. Arrêtez la capture. La transmission qui nous intéresse est noyée parmi d’autres trames qui nous parasitent. Pour simplifier la lecture, filtrez la capture de manière à ce que soient affichées uniquement les trames émises par votre machine ou à destination de votre machine.
   - **Attention :** les trames ethernet ne contiennent pas toujours un paquet IP.
   - Pour savoir comment utiliser les filtres d’affichage, référez-vous à l’aide de Wireshark : https://www.wireshark.org/docs/wsug_html_chunked/ChWorkDisplayFilterSection.html

7. Quel est le protocole utilisé pour tester la connectivité IP ? Ce protocole est le couteau suisse d’IP. Il ne sert pas seulement à tester la connectivité IP.
   - Quel est le type et le code des messages de requête et de réponse ?

8. La plupart des protocoles réseau permettent, dans l’entête, de spécifier quel est le type du contenu véhiculé.
   - Donnez le code du contenu de la trame ethernet.
   - Donnez le code du contenu du paquet IP.
   - **Contexte :** Les paquets IP transmis à votre voisin sont encapsulés dans des trames ethernet. Pour que ces trames parviennent à destination, il faut connaître l’adresse ethernet de votre voisin. Cette adresse est aussi appelée adresse matérielle ou adresse MAC (*Media Access Control*), ou encore adresse de couche liaison.

9. Avant l’envoi du ping IPv4, un échange de messages ARP (*Address Resolution Protocol*) a eu lieu.
   - Quelle est l’adresse matérielle de destination de la requête ?
   - Que signifie cette adresse ?
   - Quelle est la question posée par la requête ?

10. Avant l’envoi du ping IPv6, un échange de messages ICMPv6 de type *Neighbor Solicitation* et *Neighbor Advertisement* a eu lieu.
    - Quelle est l’adresse matérielle de destination de la requête ?
    - Que signifie cette adresse ?
    - Quelle est l’adresse IP de destination de la requête ?
    - A quoi correspond cette adresse ?

11. Affichez la liste des correspondances entre adresses IP et adresses ethernet.
    - Vérifiez que l’adresse IPv4 et l’adresse IPv6 de votre voisin y figurent, associées à son adresse MAC.
    - **Attention :** les entrées de ce cache ont une durée de vie limitée à quelques minutes.

12. A quelles couches du modèle OSI appartiennent les protocoles ethernet, IP, ICMP ?

13. Selon vous, de manière générale, pourquoi utilise-t-on l'adresse IP et non uniquement l'adresse MAC pour les communications réseaux ?