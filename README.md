# Job Listings Management System

## Overview
The **Job Listings Management System** is a Java desktop application that allows users to manage job postings stored in a MySQL database. The system provides functionality to **view, add, update, delete, filter, sort, export job listings, and calculate statistics**.

The application uses **Java Swing** for the graphical user interface and **MySQL** for data storage. It was developed as part of the **ECE318 Software Engineering course**.

---

# Features

## Job Management
- Add new job postings
- Update existing job listings
- Delete job listings
- View job listings in a table

## Data Operations
- Sort job listings
- Filter job listings
- Export job listings to CSV

## Data Analysis
- Calculate statistics from job data

## Table Interaction
- Load additional rows into the table
- Retrieve and display job information

---

# System Architecture

The system follows an **object-oriented design**. Each class is responsible for a specific functionality.

## Main Classes

### Job
Represents the basic job information.

Attributes include:
- Job ID
- Job Title
- Location
- Country
- Latitude
- Longitude
- Work Type
- Salary Range
- Company Size
- Company Name

---

### ReadJob
Extends the `Job` class and contains additional job posting information.

Additional attributes include:

- Experience
- Qualifications
- Job Posting Date
- Preference
- Contact Person
- Contact Information
- Role
- Job Portal
- Job Description
- Benefits
- Skills
- Responsibilities
- Company Profile JSON

---

### Database (Abstract Class)
Defines the interface used to interact with the database.

Main operations include:

- Add data to the database
- Delete records
- Update existing records
- Check if a record exists
- Filter job listings
- Retrieve job statistics

---

### DatabaseActions
Implements the database operations defined in the abstract database class.

Responsibilities include:

- Inserting job records
- Updating job records
- Deleting job records
- Filtering job listings
- Calculating statistics

---

### TableData
Handles loading data from the database and populating the GUI table.

Functions include:

- Retrieving rows from the database
- Managing table columns
- Returning cell values
- Incrementally loading data

---

### TablePanelWrapper
Wrapper class for GUI components used to display job listings.

Contains:

- `JPanel`
- `JTable`
- `DefaultTableModel`

---

### Message
Handles message display within the GUI interface.

---

### IntegerOnly
Utility class used to validate user input.

Validation includes:

- Integer values
- Double values
- Date formats

---

# Use Case Summary

The user interacts with the system through a graphical interface and can perform the following actions:

- View job listings
- Add job listings
- Update job listings
- Delete job listings
- Filter job listings
- Sort job listings
- Export job listings
- Calculate job statistics
- Load additional data into the table

---

# Technologies Used

- **Java**
- **Java Swing**
- **MySQL**
- **JDBC (MySQL Connector)**

---

# Project Structure

```
ECE318_PROJECT
│
├── dataset
│   └── project.sql
│
├── Diagrams
│   ├── ClassDiagram.png
│   └── UseCaseDiagram.png
│
├── src
│   └── (Java source code)
│
├── mysql-connector-j-9.1.0.jar
│
├── output.csv
│
└── README.md
```

---

# How to Run the Project

## 1. Clone the repository

```bash
git clone https://github.com/MariosBou/JobListings.git
```

---

## 2. Import into IntelliJ IDEA

Open the project folder in IntelliJ.

---

## 3. Setup the database

Create a MySQL database and import:

```
dataset/project.sql
```

---

## 4. Configure database credentials

Update the database credentials in the `Database` configuration:

```
URL
USER
PASSWORD
```

---

## 5. Run the application

Run the main Java class to launch the GUI.

---

# Future Improvements

Possible future improvements include:

- Adding authentication for users
- Implementing a web interface
- Integrating external job APIs
- Adding advanced analytics and visualization

---

# Author

**Marios Bou Mansour**

Computer Engineering Student  
University of Cyprus
