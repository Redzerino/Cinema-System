@echo off
echo Starting NovaPlex Cinema System...
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d bin src/*.java
java -cp "bin;lib/mysql-connector-j-9.6.0.jar" Main
pause