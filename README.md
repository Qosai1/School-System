# 📚 School Management System - Android App

An Android-based School Management System that enables students, teachers, and registrars to interact efficiently via a mobile platform. This app is built using Java, MySQL, and PHP with REST APIs and applies modern Android development practices.

## 🚀 Features

### 🎓 Students
- View personal class schedules.
- View grades and exam marks.
- Submit assignments.

### 👨‍🏫 Teachers
- View weekly teaching schedules.
- Assign and publish grades for students.
- Create and distribute assignments.

### 🧑‍💼 Registrars
- Add new students, teachers, and subjects.
- Build and manage class schedules.
- Maintain student and teacher profiles.

## 🛠️ Technologies Used

| Layer             | Tools & Technologies                                |
|------------------|------------------------------------------------------|
| Frontend         | Android (Java), Android Studio                       |
| Backend API      | PHP (RESTful services using `Volley` in Android)     |
| Database         | MySQL (hosted locally)                               |
| Communication    | JSON-based API via HTTP using Android's `Volley`     |
| Storage          | SharedPreferences, Local Images                     |
| UI Components    | Spinner, TableLayout, TextView, Buttons, ScrollView |
| Architecture     | MVC-like separation with modular PHP APIs            |

## 🗂 Project Structure

📁 Android App
├── MainActivity.java # Main interface for schedule submission
├── StudentScheduleActivity.java # View schedules dynamically
├── res/
│ ├── layout/ # Layout XML files (Constraint, Linear...)
│ └── values/ # Centralized styles and strings
📁 Backend PHP APIs
├── db.php # DB connection setup
├── insert_schedule.php # Insert schedule records
├── get_student_schedule.php # Fetch student schedules by class
├── check_schedule.php # Verify schedule existence
📁 Database
└── s_system.sql # Full schema of MySQL DB with sample data

markdown
Copy
Edit

## 🧪 How It Works

1. **Database Setup**:
   - Import `s_system.sql` into your MySQL server.
   - Ensure PHPMyAdmin or XAMPP is running (`localhost/phpmyadmin`).

2. **Backend Configuration**:
   - Place the PHP files (`db.php`, `insert_schedule.php`, etc.) inside the `htdocs` folder of XAMPP.
   - Edit `db.php` if your DB username or password differs from default.

3. **Android Integration**:
   - Open project in Android Studio.
   - Update IP address in the Java code to match your server (e.g., `10.0.2.2` for emulator).
   - Use emulator or physical device for testing.

## 📌 Notable Database Tables

| Table              | Description                                  |
|--------------------|----------------------------------------------|
| `students`         | Stores student information                   |
| `teachers`         | Stores teacher information                   |
| `subjects`         | Subjects offered in the school               |
| `schedules`        | Class-level schedule headers                 |
| `schedule_details` | Day-wise schedule breakdown                  |
| `marks`            | Exam marks for students                      |
| `assignments`      | Assignment info posted by teachers           |
| `submissions`      | Assignment submissions by students           |
| `users`            | Auth table for login and role identification|
