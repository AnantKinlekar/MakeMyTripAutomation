# MakeMyTrip Automation – Date Selection Using Java LocalDate

This repository contains an **automation framework for the MakeMyTrip website**, focusing on dynamic date selection logic using Java’s modern date-time API.

The automation handles:
- Selecting **From** and **To** cities
- Selecting a **travel date 5 days from the current date**
- Automatically handling **month transitions** (current month → next month)

---

## 🚀 Key Features

- Dynamic date calculation using `java.time.LocalDate`
- Automatic handling of:
  - End-of-month scenarios
  - Next-month calendar navigation
- Clean and maintainable Selenium automation logic
- No hardcoded dates (fully scalable)

---

## 🛠 Tech Stack

- **Language:** Java  
- **Automation Tool:** Selenium WebDriver  
- **Build Tool:** Maven  
- **Browser:** Google Chrome  
- **Date Handling:** `java.time.LocalDate`  

---

## 📅 Date Selection Logic

The travel date is calculated dynamically as:

```java
LocalDate travelDate = LocalDate.now().plusDays(5);
