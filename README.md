# 🏥 Healthcare Data Migration & API

This project demonstrates **healthcare data migration** from a legacy **SQL Server** database to **AWS RDS (PostgreSQL)** and **DynamoDB** using **AWS DMS**, along with a **Spring Boot REST API** for managing hospital operations.

It provides:
- **Data Migration** templates (`aws-dms/`) for SQL Server ➝ AWS RDS & DynamoDB.
- **RESTful APIs** for **Patient Registration**, **Appointments**, and **Billing**.
- **Audit Logging** in DynamoDB for migration and API activity.
- **Database setup** with schema + seed data.

---

## 🚀 Features
- **Spring Boot 3 + Java 17** backend
- **JPA + Hibernate** with PostgreSQL/MySQL support
- **AWS SDK v2** (DynamoDB Enhanced Client)
- **Data Migration via AWS DMS** (full load + CDC)
- **Swagger/OpenAPI** documentation
- Pre-seeded DB data (`schema.sql`, `data.sql`)

---

## 📂 Project Structure
```

healthcare-data-migration-api/
│── src/main/java/com/healthcare/
│ ├── Application.java # Main Spring Boot entrypoint
│ ├── domain/ # Entities: Patient, Appointment, Billing
│ ├── dto/ # DTO classes for requests/responses
│ ├── repository/ # Spring Data JPA repositories
│ ├── service/ # Business services + DynamoDB Audit service
│ ├── controller/ # REST controllers
│ ├── config/ # AWS DynamoDB config
│ ├── dynamodb/ # DynamoDB models (AuditLog)
│ ├── exception/ # Exception handling
│ └── mapper/ # DTO ↔ Entity mappers
│
│── src/main/resources/
│ ├── application.yml # Spring Boot configuration
│ ├── schema.sql # (Optional) Schema definitions
│ └── data.sql # Seed data
│
│── aws-dms/
│ ├── dms-endpoints.json # Example source/target endpoint config
│ ├── dms-task-settings.json # Example migration task (Full load + CDC)
│ └── aws-cli-example.md # CLI usage examples
│
│── pom.xml # Maven dependencies
│── README.md # Project documentation

````

---

## 🛠️ Tech Stack
- **Java 17**, **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL / MySQL** (RDS target DB)
- **AWS DynamoDB** (Audit logs)
- **AWS DMS** (Data Migration Service)
- **Swagger UI (OpenAPI)**

---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repo
```bash
git clone https://github.com/your-username/healthcare-data-migration-api.git
cd healthcare-data-migration-api
````

### 2️⃣ Run Database (Postgres via Docker)

```bash
docker run --name healthcare-db -e POSTGRES_USER=healthcare -e POSTGRES_PASSWORD=healthcare -e POSTGRES_DB=healthcare -p 5432:5432 -d postgres:15
```

For MySQL instead:

```bash
docker run --name healthcare-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=healthcare -e MYSQL_USER=healthcare -e MYSQL_PASSWORD=healthcare -p 3306:3306 -d mysql:8
```

### 3️⃣ Configure Application

Edit `src/main/resources/application.yml` to set DB credentials and AWS region/table.

Switch DB profiles:

```bash
# Default (Postgres)
SPRING_PROFILES_ACTIVE=dev

# MySQL
SPRING_PROFILES_ACTIVE=mysql
```

### 4️⃣ Build & Run

```bash
./mvnw clean package
./mvnw spring-boot:run
```

---

## 📖 API Endpoints

Swagger UI available at:
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

| Method | Endpoint            | Description              |
| ------ | ------------------- | ------------------------ |
| GET    | `/api/patients`     | Get all patients         |
| POST   | `/api/patients`     | Create new patient       |
| GET    | `/api/appointments` | Get all appointments     |
| POST   | `/api/appointments` | Schedule appointment     |
| GET    | `/api/billings`     | Get billing records      |
| POST   | `/api/billings`     | Create new billing entry |

---

## ☁️ AWS DMS Setup

1. **Create replication instance** in AWS DMS.
2. **Configure endpoints**:

   * `aws-dms/dms-endpoints.json`
   * Update hostnames, ports, usernames, and passwords.
3. **Create replication task** using:

   * `aws-dms/dms-task-settings.json`
4. **Run with CLI**:

```bash
aws dms create-endpoint --cli-input-json file://aws-dms/dms-endpoints.json
aws dms create-replication-task --cli-input-json file://aws-dms/dms-task-settings.json
aws dms start-replication-task --replication-task-arn <TASK_ARN> --start-replication-task-type start-replication
```

Detailed commands are in `aws-dms/aws-cli-example.md`.

---

## 🔎 Verification

* Connect to **RDS PostgreSQL** and check `patients`, `appointments`, `billings` tables.
* Check **DynamoDB audit logs** for migration activity.
* Test REST APIs with **Swagger UI**.

---

## 🧪 Example DB Seed

After startup, DB is preloaded with:

* Patient: `Asha Verma` (`MRN1001`)
* Appointment: Cardiology with `Dr. Smith`
* Billing: `₹1200.50` pending

---

## 📌 Next Steps

* Add **authentication & JWT security**
* Extend modules: pharmacy, lab results
* Set up **CI/CD pipeline** for AWS deployment

---

## 👨‍💻 Author

**Harsh Sonkar**
AWS Engineer | Data Engineer | Full-Stack Developer

---


