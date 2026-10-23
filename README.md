![NovaPlex Cinema](images/Banner.png)

# NovaPlex Cinema System

A Java Swing desktop application for running a cinema: movies, showtimes, screens, seats, bookings, tickets, payments and staff accounts, all backed by a 10-table MySQL database through JDBC.

▶️ **[Watch the walkthrough video](https://youtu.be/F7H5ujrvQqM)**

## Features

- **Admin login** checked against the database (active Admin accounts only)
- **11 management tabs**: Movies, Showtimes, Bookings, Screens, Seats, Users, Payments, Tickets, Seat Bookings, Seat Reserves and Reports
- **Full create, update and retire/cancel actions** on each table, with consistent styled buttons
- **Seat reservations that expire automatically**: a held seat is released after 15 minutes, checked by a background timer every minute
- **Sales & Bookings report** with total bookings, total revenue and status counts, filterable by All, Confirmed, Cancelled and Pending
- **Guest and registered-customer bookings**, each with its own booking reference
- **Configurable database connection** through `db.properties`, so credentials aren't hard-coded

## Tech stack

| Area | Technology |
|---|---|
| Language | Java (OOP) |
| GUI | Java Swing |
| Database | MySQL 8 |
| Data access | JDBC with the DAO pattern, using prepared statements for all inserts, updates and logins |
| Docs | Javadoc (in [`api/`](api/)) and a [system document](NovaPlexDocument.pdf) |

## Database

The schema and sample data are in [`database/novaplexdb.sql`](database/novaplexdb.sql). The 10 tables are:

`movies` · `screens` · `seats` · `showtimes` · `users` · `bookings` · `seatreserve` · `seatbooking` · `tickets` · `payments`

Bookings link showtimes to either a registered user or a guest. Seats are first held in `seatreserve` with an expiry time, then confirmed in `seatbooking`, and each booking has its tickets and payment recorded against it.

## Getting started

### Requirements
- Java 17 or later
- MySQL 8 (MySQL Workbench is handy but optional)

### 1. Set up the database

Either import `database/novaplexdb.sql` in MySQL Workbench (**Server → Data Import → Import from Self-Contained File**), or run:

```bash
mysql -u root -p < database/novaplexdb.sql
```

This creates the `novaplexdb` database with sample data.

### 2. Add your database login

Copy `db.properties.example` to `db.properties` and enter your MySQL username and password. If you skip this, the app tries `root` / `root` on `localhost`.

### 3. Run the app

- **Windows:** double-click `run.bat`
- **macOS / Linux:** `./run.sh`

Or compile and run it yourself:

```bash
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d bin src/*.java
java -cp "bin;lib/mysql-connector-j-9.6.0.jar" Main     # Windows
java -cp "bin:lib/mysql-connector-j-9.6.0.jar" Main     # macOS / Linux
```

### Demo login

| Email | Password |
|---|---|
| `sarahcrotty@novaplex.com` | `sarah001` |

## Project structure

```
src/        Java source: models, DAOs, GUI panels and entry point (Main.java)
database/   MySQL schema and sample data
lib/        MySQL JDBC driver
images/     Logo and banner
api/        Generated Javadoc
```

The code follows a layered design:

- **Models** (`Movie`, `Booking`, `Seat` …) hold the data for each table.
- **DAOs** (`MovieDAO`, `BookingDAO` …) handle all SQL for their table.
- **Panels** (`MoviePanel`, `BookingPanel` …) build each tab of the GUI.
- `GUIHelper` keeps buttons and styling consistent, and `DBConnection` manages the connection.

## About

Built by Sarah Crotty for the Object-Oriented Software Development module (CA3) of the BA (Hons) Software Development at SETU Carlow.
