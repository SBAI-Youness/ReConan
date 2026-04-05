# ReConan Development Guide

## Phase 1 — Project Setup & Database

### Goals
- Setup Java project
- Connect to SQL Server
- Implement database schema

### Tasks
- Create Maven/Gradle project
- Add dependencies (JDBC, Jackson/Gson, JGraphT)
- Create database and tables
- Add indexes for performance
- Implement DatabaseConnection.java
- Use config file for DB credentials
- Implement Repository layer (CRUD operations)

---

## Phase 2 — Core System + UI

### Goals
- Build basic UI
- Implement target management

### Tasks
- Create JavaFX UI (Dashboard, forms)
- Add/Edit/Delete/Search targets
- Connect UI with database

---

## Phase 3 — OSINT Integrations

### Goals
- Fetch real-world data

### Tasks
- Implement WhoisClient, DnsClient
- Parse API responses
- Convert data into entities
- Create relationships between entities
- Ensure no duplicate entities

---

## Phase 4 — Graph System

### Goals
- Visualize relationships

### Tasks
- Build graph using JGraphT
- Implement GraphService
- Create graph UI (nodes & edges)
- Add node expansion & interaction

---

## Phase 5 — Transform Engine

### Goals
- Automate data expansion

### Tasks
- Create Transform interface
- Implement transforms (DNS, WHOIS, etc.)
- Build TransformEngine
- Enable chaining of transforms

---

## Phase 6 — Final Features

### Goals
- Polish and finalize

### Tasks
- Add logging system
- Export to CSV
- Save graph snapshots
- Track investigation history

---

## Cross-Platform Support

- Use Java + JavaFX (cross-platform by default)
- Avoid OS-specific code
- Package app using jpackage
- Support Windows, Linux, macOS
- Consider SQL Server Express or Docker setup

---

## Collaboration Guide

### Tools
- GitHub for version control

### Workflow
- main branch (stable)
- dev branch (development)
- feature branches (specific features)

### Rules
- Use pull requests
- Code review before merging
- Follow consistent structure

### Team Roles
- UI developer
- Backend/database developer
- OSINT integration developer
- Graph system developer

### Communication
- Use Discord or Slack
- Weekly meetings

### Documentation
- Maintain README
- Document database schema
- Keep API notes

---

## Final Advice

1. Make it work
2. Make it clean
3. Make it powerful
