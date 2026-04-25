# NovaPlex Cinema System

A Java Swing desktop app for managing cinema operations — movies, bookings, seats, payments and more. Built with JDBC and MySQL for my OOSD CA3.

## How to Run

You'll need Java and MySQL installed, and the novaplexdb database set up first.

**Import the database:**
1. Open MySQL Workbench
2. Go to Server → Data Import
3. Import the `novaplexdb.sql` file

**Run the app:**

Either double click `run.bat` or run manually:

```
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d bin src/*.java
java -cp "bin;lib/mysql-connector-j-9.6.0.jar" Main
```

