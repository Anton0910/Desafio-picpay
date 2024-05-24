#Importa maven de uma imagem ja existente
FROM maven:3.6.3-openjdk-17 AS build

#Tras todos os arquivos de toda a aplicação para o container
COPY src /app/src

#Tras o aquivo pom pra dendo do app
COPY pom.xml /app

#Muda para o diretorio app
WORKDIR /app

#Vai baixar as dependencias e fazer o build
RUN mvn clean install

#Pega a imagem do jdk da AWS
FROM openjdk:17-jdk

#Copia o arquivo do biuld e leva pro container
COPY --from=build /app/target/desafio_picpay-0.0.1-SNAPSHOT.jar ./app.jar

#Muda para o diretorio app
WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]