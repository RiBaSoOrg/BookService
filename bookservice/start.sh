#!/bin/bash

# Warten, bis der Datenbankdienst bereit ist
echo "Warte auf PostgreSQL..."
until nc -z -v -w30 book_postgres 5432
do
  echo "Warten auf PostgreSQL..."
  sleep 1
done
echo "PostgreSQL gestartet"

# Starten Sie den bookservice
java -jar /app/bookservice.jar &

# Warten, bis der bookservice gestartet ist
echo "Warte auf bookservice..."
until nc -z -v -w30 localhost 8080
do
  echo "Warten auf bookservice..."
  sleep 1
done
echo "bookservice gestartet"

# Führen Sie den curl-Befehl aus
curl -X 'GET' 'http://localhost:8080/books/sync-books' -H 'accept: */*'

# Halten Sie den Container am Laufen
wait
