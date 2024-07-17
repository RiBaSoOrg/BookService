# Verwende ein offizielles OpenJDK-Image als Basis
FROM openjdk:17-jdk-slim

# Erstelle ein Verzeichnis für die Anwendung
VOLUME /tmp

# Kopiere die JAR-Datei in das Image
ARG JAR_FILE=target/bookservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Kopiere das Start-Skript in das Image
COPY start.sh /app/start.sh

# Mache das Start-Skript ausführbar
RUN chmod +x /app/start.sh

# Exponiere den Port, auf dem die Anwendung läuft
EXPOSE 8080

# Starte das Start-Skript
ENTRYPOINT ["/app/start.sh"]
