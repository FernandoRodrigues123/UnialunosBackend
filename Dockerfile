# ---------- STAGE 1: build ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia apenas os arquivos necessários para cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do projeto
COPY src ./src

# Build do jar
RUN mvn clean package -DskipTests


# ---------- STAGE 2: runtime ----------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o jar gerado no stage de build
COPY --from=build /app/target/AlunosProjeto-0.0.1-SNAPSHOT.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Variáveis úteis (opcional, mas ajuda em cloud)
ENV JAVA_OPTS=""

# Start da aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
