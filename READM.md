# 🎓 Projet d'Initiation - E2IM

**Étudiant :** ELKHAL ASAAD / ANASS AIT LHOUSSAINI  
**Classe :** Informatique et Système d'information - Option Informatique  
**Université :** Université Privée de Marrakech - E2IM  
**Année :** 2024-2025

---

## 📋 Description du Projet

Ce projet comporte deux applications Java indépendantes :

1. **Système de Gestion Universitaire** - Application console avec base de données MySQL
2. **Chat Sécurisé Client/Serveur** - Application réseau avec chiffrement AES/RSA

---

## 🗂️ Structure du Projet

```
TP-Init/
├── SRC....       #Gestion des étudiants et cours
└── CHAT           # Chat sécurisé avec chiffrement
```

---

## 🚀 Installation et Exécution

### Prérequis

- **Java JDK 8+** (Java 11+ recommandé)
- **MySQL 5.7+** ou **PostgreSQL** (pour la partie 1)
- **IDE** : IntelliJ IDEA, Eclipse, ou VS Code (optionnel)

### Partie 1 : Gestion Universitaire

```bash
cd TP_INIT
# Voir README.md dans ce dossier pour les instructions détaillées
```

### Partie 2 : Chat Sécurisé

```bash
cd CHAT
# Voir README.md dans ce dossier pour les instructions détaillées
```

---

## 🎯 Fonctionnalités Implémentées

### ✅ Partie 1 - Gestion Universitaire

- [x] Lister les étudiants
- [x] Ajouter un étudiant
- [x] Lister les cours
- [x] Ajouter un cours
- [x] Inscrire un étudiant à un cours
- [x] Lister les cours d'un étudiant
- [x] Supprimer un étudiant (avec ses inscriptions)

**Architecture :**
- Couche DAO (Data Access Object)
- Couche Service (logique métier)
- Interface console

**Technologies :**
- JDBC (Java Database Connectivity)
- MySQL
- PreparedStatement (sécurité SQL)

### ✅ Partie 2 - Chat Sécurisé

- [x] Connexion client/serveur via sockets TCP
- [x] Support multi-clients avec threads
- [x] Chiffrement AES des messages
- [x] Échange de clés sécurisé avec RSA
- [x] Notifications de connexion/déconnexion

**Architecture :**
- Serveur multi-threads
- Chiffrement hybride (RSA + AES)
- Sérialisation d'objets Java

**Technologies :**
- Sockets TCP (ServerSocket/Socket)
- Threads Java
- javax.crypto (AES, RSA)
- ObjectInputStream/ObjectOutputStream

---

## 📚 Concepts Techniques Utilisés

### Partie 1
- **Pattern DAO** : Séparation accès données / logique métier
- **JDBC** : Connexion et requêtes SQL
- **PreparedStatement** : Prévention injection SQL
- **Try-with-resources** : Gestion automatique des ressources
- **Relations many-to-many** : Table d'association

### Partie 2
- **Sockets TCP** : Communication réseau
- **Multi-threading** : Thread par client
- **Chiffrement symétrique (AES)** : Chiffrement rapide des messages
- **Chiffrement asymétrique (RSA)** : Échange sécurisé de clé
- **Sérialisation Java** : Transmission d'objets sur le réseau

---

## 📸 Captures d'écran

### Partie 1 - Menu Principal
```
╔════════════════════════════════════╗
║         MENU PRINCIPAL             ║
╠════════════════════════════════════╣
║ 1. Lister les étudiants            ║
║ 2. Ajouter un étudiant             ║
║ 3. Lister les cours                ║
...
```

### Partie 2 - Chat en Action
```
[14:30:15] *** Ahmed a rejoint le chat ***
[14:30:20] Ahmed: Bonjour tout le monde!
[14:30:25] Fatima: Salut Ahmed!
```

---

## 🛠️ Améliorations Possibles

- [ ] Interface graphique (JavaFX/Swing)
- [ ] Authentification des utilisateurs
- [ ] Historique des messages dans une base de données
- [ ] Partage de fichiers dans le chat
- [ ] Intégration : chat accessible uniquement aux étudiants inscrits

---

## 📄 Licence

Projet académique - E2IM - Université Privée de Marrakech

---

## 👤 Auteur

**ELKHAL ASAAD**  
Email : asaadelkhal@gmail.com

GitHub : [@is-ELKHAL](https://github.com/is-ELKHAL)

---

## 🙏 Remerciements

- **Encadrant :** BENTAJER AHMED
- **École :** E2IM - Université Privée de Marrakech
- **Promotion :** 2025
