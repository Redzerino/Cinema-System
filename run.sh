#!/bin/sh
# Compiles and starts NovaPlex on macOS / Linux
echo "Starting NovaPlex Cinema System..."
mkdir -p bin
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d bin src/*.java && \
java -cp "bin:lib/mysql-connector-j-9.6.0.jar" Main
