# Tripadvisor

![badge-github](https://github.com/Shyrogan/tripadvisor-backend/actions/workflows/gradle.yml/badge.svg)

## Getting started

**(!) Ces services ne correspondent qu'au backend de l'application finale, il vous faut aussi
télécharger l'application frontend (!)**

Il est facile de construire les JAR de chacun des services avec Gradle.

```shell
./gradlew :hotel-service:shadowJar
./gradlew :agence-service:shadowJar
./gradlew :tripadvisor-service:shadowJar
```

Afin de lancer le programme, il est nécessaire d'avoir une base de donnée [PostgreSQL](https://postgresql.org/).

Si vous vous demandez quels sont les arguments à passer pour démarrer le programme, ceux-ci sont
capable de vous indiquer les paramètres nécessaires/manquants:

```shell
java -jar hotel-service.jar       # Affiche l'aide pour créer un hôtel
java -jar agence-service.jar      # Affiche l'aide pour créer une agence
java -jar tripadvisor-service.jar # Affiche l'aide pour lancer le comparateur de prix
```

## Contributeurs

- [VIAL Sébastien](https://github.com/Shyrogan)
- [EL-JAAFARI Samy](https://github.com/S-elj)