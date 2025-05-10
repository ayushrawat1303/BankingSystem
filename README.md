# 🏦 Spring Boot Banking Application

This is a simple RESTful banking system built with **Spring Boot** that supports essential banking operations like creating users, depositing and withdrawing funds, transferring money between accounts, and closing accounts.

---

## 📌 Features

- ✅ Add New User (`/addUser`)
- 💰 Credit Account (`/credit`)
- 💸 Debit Account (`/debit`)
- 🔍 Balance Enquiry (`/balance`)
- 🔁 Transfer Funds Between Accounts (`/transfer`)
- ❌ Close Bank Account (`/close`)

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **H2 / MySQL** (configurable)
- **Lombok**
- **Postman** for API testing

## 🚀 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/YOUR_REPO.git
   cd YOUR_REPO
   ```

2. **Make sure you have Java and Maven installed**

3. **Run the app**
   ```bash
   ./mvnw spring-boot:run
   ```

4. App will start at: `http://localhost:8080`

---

## 📬 API Endpoints

| Operation            | Endpoint       | Method | Description                                |
|----------------------|----------------|--------|--------------------------------------------|
| Add User             | `/addUser`     | POST   | Creates a new user/account                 |
| Credit Amount        | `/credit`      | POST   | Adds money to user's account               |
| Debit Amount         | `/debit`       | POST   | Deducts money from user's account          |
| Balance Enquiry      | `/balance`     | GET    | Fetches current account balance            |
| Transfer Funds       | `/transfer`    | POST   | Transfers money between two accounts       |
| Close Account        | `/close`       | DELETE | Closes the user’s bank account             |

> You can test all these endpoints using **Postman**.

---

## 📝 Sample JSON Request – Transfer

```json
{
  "accountNumber1": "1234567890",
  "transferAmt": 5000,
  "accountNumber2": "0987654321"
}
```


## 👨‍💻 Author

**Ayush Rawat**  
GitHub: [@ayushrawat1303](https://github.com/ayushrawat1303)
