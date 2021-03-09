#!/bin/ash

FROM openjdk:8u171-jdk-alpine3.8

LABEL maintainer="rospielberg@gmail.com"

ENV LANG C.UTF-8

# Atualizar
RUN apk add --update bash

# Pegando o jar da aplicação e colocando na pasta para executar
ADD build/libs/*.jar /app/app.jar

# Executando, estamos passando uma variável pra que seja possível dinamizar propriedades do application.properties
# Neste caso vamos mudar o endereço de acesso do banco deixando de passar localhost mas o nome do contêiner
CMD java -jar /app/app.jar $APP_OPTIONS