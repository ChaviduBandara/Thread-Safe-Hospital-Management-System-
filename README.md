# 🏥 Concurrent Hospital Management System

A concurrent hospital management system built using **Java** to simulate and manage multiple hospital operations safely and efficiently, with a focus on **synchronization**, **thread safety**, and **reliable handling of shared resources**.

---

## 📌 Overview

This project simulates a real-world hospital environment where multiple operations — such as patient admissions, doctor assignments, and resource allocation — occur simultaneously. The system is designed to handle concurrent access to shared resources without race conditions or deadlocks, ensuring safe and consistent operation at all times.

---

## ✨ Key Features

- **Concurrent patient management** — multiple patients handled simultaneously using multithreading
- **Thread-safe resource allocation** — shared hospital resources managed safely across threads
- **Synchronization mechanisms** — Java synchronization primitives used to prevent race conditions
- **Deadlock prevention** — careful lock ordering and resource management to avoid deadlocks
- **Efficient thread handling** — Java thread pools and concurrent utilities for optimal performance

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Java 21** | Primary programming language |
| **Java Concurrency API** | Thread management and synchronization |
| **Maven** | Build and dependency management |

---

## 🔧 Concurrency Concepts Used

- **Synchronized methods and blocks** — protecting critical sections
- **Thread safety** — ensuring consistent state across multiple threads
- **Shared resource management** — safe access to common hospital resources
- **Java threading** — Thread, Runnable, ExecutorService
- **Locks and monitors** — coordinating thread execution

---

## 🚀 How to Run

### Prerequisites
- Java 21 or higher
- Maven

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/yourusername/hospital-management-system.git
cd hospital-management-system
```

**2. Build the project**
```bash
mvn clean install
```

**3. Run the application**
```bash
mvn exec:java -Dexec.mainClass="edu.icet.Main"
```

---

