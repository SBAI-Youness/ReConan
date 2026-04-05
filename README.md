# ReConan

**ReConan — OSINT & Cyber Investigation Platform**

ReConan is a desktop-based Open Source Intelligence (OSINT) and cyber investigation platform. The goal of the project is to collect intelligence from multiple public data sources, store it in a structured database, analyze relationships between entities, and visualize those relationships through an interactive investigation graph.

The platform is designed primarily as an educational cybersecurity project implemented using **Java** and **SQL Server**.

---

# 1. Project Goals

The main objectives of ReConan are:

* Aggregate intelligence from multiple OSINT sources  
* Store investigation data in a structured database  
* Discover relationships between digital entities  
* Provide investigators with an interactive graph interface  
* Maintain investigation logs and exportable reports  

ReConan focuses on **digital reconnaissance and relationship analysis**, allowing investigators to explore how domains, IP addresses, emails, and online identities connect.

---

# 2. Core Concept

ReConan works around a **graph-based investigation model implemented on a relational database**.

Every investigation consists of:

**Entities (Nodes)**

* Domain
* IP address
* Email
* Username
* Organization
* Phone number

**Relationships (Edges)**

* Domain → hosted on → IP
* Email → belongs to → Person
* Username → found on → Platform

The platform stores and processes these relationships using SQL Server and application-level graph logic.

---

# 3. Technology Stack

## Programming Language
Java

## User Interface
JavaFX

## Database
SQL Server

## Data Access
JDBC

## JSON Parsing
Jackson or Gson

## Graph Modeling (Application-Level)
JGraphT

## Networking
Java HTTP Client

## Packaging
jpackage

---

# 4. System Architecture

ReConan follows a **layered architecture** to keep the project modular and maintainable.

```
+---------------------------+
|        User Interface     |
|        (JavaFX)           |
+-------------+-------------+
              |
+-------------v-------------+
|     Application Layer     |
|  Investigation Services   |
|  Transform Engine         |
|  Graph Manager            |
+-------------+-------------+
              |
+-------------v-------------+
|        Data Layer         |
|  Repository / DAO         |
|  Entity Models            |
+-------------+-------------+
              |
+-------------v-------------+
|        Database           |
|        SQL Server         |
+---------------------------+
```

---

# 5. Database Schema

## Tables

### Targets
* id
* type
* value
* created_at

### Entities
* id
* type
* value
* source

### Relationships
* id
* entity_source
* entity_target
* relationship_type

### Sources
* id
* name
* api_endpoint

### Logs
* id
* action
* timestamp
* target

---

# 6. Application Features

## Target Management

* Add, edit, delete targets  
* Search investigation history  

## OSINT Data Collection

* WHOIS services  
* DNS lookup  
* Public APIs  

## Graph Visualization

* Node visualization  
* Relationship edges  
* Zoom and pan  
* Node expansion  
* Graph filtering  

## Transform Engine

Automated entity expansion:

* WHOIS lookup  
* DNS resolution  
* Subdomain discovery  

## Investigation History

* Search logs  
* API requests  
* Generated entities  

## Reporting

* CSV exports  
* Graph snapshots  
* Investigation summaries  

---

# 7. Project Structure

```
reconan/

src/
 ├── main/
 │   ├── java/
 │   │   ├── reconan/
 │   │   │   ├── Main.java
 │   │   │   ├── ui/
 │   │   │   ├── service/
 │   │   │   ├── osint/
 │   │   │   ├── model/
 │   │   │   ├── repository/
 │   │   │   └── database/
 │   └── resources/
```

---

# 8. Development Roadmap

## Phase 1
Project setup & database

## Phase 2
Core system & UI

## Phase 3
OSINT integrations

## Phase 4
Graph system (application-level)

## Phase 5
Transforms

## Phase 6
Final features & packaging

---

# 9. Distribution

* SQL Server required for development and usage  
* Packaged using jpackage  

---

# 10. Future Improvements

* More OSINT sources  
* Automated investigation pipelines  
* Risk scoring system  
* AI-assisted analysis  
* Collaboration features  

---

# Conclusion

ReConan is a graph-based OSINT investigation platform built on top of a relational database system. It demonstrates how structured data storage and application-level graph processing can be combined to analyze and visualize digital relationships in cybersecurity investigations.
