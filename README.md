# ReConan

**ReConan — OSINT & Cyber Investigation Platform**

ReConan is a desktop-based Open Source Intelligence (OSINT) and cyber investigation platform inspired by link‑analysis tools such as Maltego. The goal of the project is to collect intelligence from multiple public data sources, store it in a structured database, analyze relationships between entities, and visualize those relationships through an interactive investigation graph.

The platform is designed primarily as an educational cybersecurity project implemented using **Java** and **SQL Server**.

---

# 1. Project Goals

The main objectives of ReConan are:

• Aggregate intelligence from multiple OSINT sources
• Store investigation data in a structured database
• Discover relationships between digital entities
• Provide investigators with an interactive graph interface
• Maintain investigation logs and exportable reports

ReConan focuses on **digital reconnaissance and relationship analysis**, allowing investigators to explore how domains, IP addresses, emails, and online identities connect.

---

# 2. Core Concept

ReConan works around a **graph-based investigation model**.

Every investigation consists of:

**Entities (Nodes)**
Examples:

* Domain
* IP address
* Email
* Username
* Organization
* Phone number

**Relationships (Edges)**
Examples:

* Domain → hosted on → IP
* Email → belongs to → Person
* Username → found on → Platform

The platform collects information from OSINT sources and automatically creates links between entities.

---

# 3. Technology Stack

## Programming Language

Java

## User Interface

JavaFX

## Database

SQL Server

(Optional distribution version: H2 embedded database)

## Data Access

JDBC

## JSON Parsing

Jackson or Gson

## Graph Modeling

JGraphT

## Networking

Java HTTP Client

## Packaging

jpackage (to generate executable installers)

---

# 4. System Architecture

ReConan follows a **layered architecture** to keep the project modular and maintainable.

```
+---------------------------+
|        User Interface     |
|        (JavaFX)           |
+-------------+-------------+
              |
              |
+-------------v-------------+
|     Application Layer     |
|  Investigation Services   |
|  Transform Engine         |
|  Graph Manager            |
+-------------+-------------+
              |
              |
+-------------v-------------+
|        Data Layer         |
|  Repository / DAO         |
|  Entity Models            |
+-------------+-------------+
              |
              |
+-------------v-------------+
|        Database           |
|        SQL Server         |
+---------------------------+
```

### Layer Responsibilities

User Interface Layer

Handles:

* Graph visualization
* Investigation dashboard
* Search inputs
* Results display

Application Layer

Handles:

* OSINT data collection
* Relationship discovery
* Investigation logic
* Transform execution

Data Layer

Handles:

* Database queries
* Entity persistence
* Data normalization

Database Layer

Stores:

* Targets
* Entities
* Relationships
* Logs
* API results

---

# 5. Database Schema

## Tables

### Targets

Stores investigation starting points.

Fields:

* id
* type
* value
* created_at

### Entities

Stores discovered nodes.

Fields:

* id
* type
* value
* source

### Relationships

Stores connections between entities.

Fields:

* id
* entity_source
* entity_target
* relationship_type

### Sources

Stores OSINT sources used.

Fields:

* id
* name
* api_endpoint

### Logs

Stores investigation history.

Fields:

* id
* action
* timestamp
* target

---

# 6. Application Features

## Target Management

Users can:

* Add investigation targets
* Edit or delete targets
* Search previously investigated targets

Supported targets include:

* Domains
* IP addresses
* Emails
* Usernames

---

## OSINT Data Collection

ReConan can retrieve data from:

* WHOIS services
* Shodan
* Social platforms
* Public APIs

The system parses responses and converts them into entities and relationships.

---

## Graph Visualization

The platform provides a **Maltego-style investigation graph**.

Capabilities:

* Node visualization
* Relationship edges
* Zoom and pan
* Node expansion
* Graph filtering

---

## Transform Engine

Transforms are automated queries that expand an entity.

Example:

Input:
Domain

Transforms:

* Get WHOIS info
* Get IP address
* Get DNS records
* Search for subdomains

Each transform may generate new nodes and relationships.

---

## Investigation History

ReConan logs:

* Search history
* API requests
* Generated entities

This helps investigators track their workflow.

---

## Reporting

Users can export:

* CSV investigation data
* Graph snapshots
* Investigation summaries

---

# 7. Project Structure

Example Java project structure:

```
reconan/

src/
 ├── main/
 │   ├── java/
 │   │   ├── reconan/
 │   │   │   ├── Main.java
 │   │   │
 │   │   │   ├── ui/
 │   │   │   │   ├── DashboardController.java
 │   │   │   │   ├── GraphView.java
 │   │   │   │   └── TargetForm.java
 │   │   │   │
 │   │   │   ├── service/
 │   │   │   │   ├── InvestigationService.java
 │   │   │   │   ├── TransformEngine.java
 │   │   │   │   └── GraphService.java
 │   │   │   │
 │   │   │   ├── osint/
 │   │   │   │   ├── WhoisClient.java
 │   │   │   │   ├── ShodanClient.java
 │   │   │   │   └── SocialSearchClient.java
 │   │   │   │
 │   │   │   ├── model/
 │   │   │   │   ├── Entity.java
 │   │   │   │   ├── Relationship.java
 │   │   │   │   ├── Target.java
 │   │   │   │   └── Source.java
 │   │   │   │
 │   │   │   ├── repository/
 │   │   │   │   ├── EntityRepository.java
 │   │   │   │   ├── RelationshipRepository.java
 │   │   │   │   └── TargetRepository.java
 │   │   │   │
 │   │   │   └── database/
 │   │   │       └── DatabaseConnection.java
 │   │
 │   └── resources/
 │       └── ui layouts
```

---

# 8. Development Roadmap

## Phase 1

Project setup

* Create Java project
* Configure SQL Server connection
* Implement basic database schema

---

## Phase 2

Core system

* Target management
* Database CRUD operations
* Basic JavaFX interface

---

## Phase 3

OSINT integrations

* WHOIS
* DNS lookup
* Basic API connectors

---

## Phase 4

Graph system

* Implement entity nodes
* Implement relationship edges
* Build visualization UI

---

## Phase 5

Transforms

* Automated entity expansion
* Multi-source intelligence

---

## Phase 6

Final features

* Reporting
* Investigation history
* Packaging and distribution

---

# 9. Distribution

For development:

SQL Server is used as the primary database.

For distribution:

The application can use **H2 embedded database**, allowing users to run ReConan without installing SQL Server.

Packaging can be done with:

```
jpackage
```

Which creates:

* Windows installer (.exe)
* Linux package
* macOS installer

---

# 10. Future Improvements

Possible improvements include:

* More OSINT sources
* Automated investigation pipelines
* Risk scoring system
* AI-assisted relationship discovery
* Collaboration features

---

# Conclusion

ReConan is a graph-based OSINT investigation platform designed for cybersecurity education and research. By combining Java, SQL Server, and graph analysis techniques, the platform demonstrates how intelligence tools can collect, connect, and visualize digital evidence.

The system provides a simplified but powerful alternative to professional intelligence analysis tools while remaining accessible for academic development.