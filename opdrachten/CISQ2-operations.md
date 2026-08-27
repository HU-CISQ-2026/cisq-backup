# CISQ2 praktijkassessment: operations

In deze opdracht maak je van de UNO-backend een reproduceerbare
releasecontainer. Software die op iedere omgeving hetzelfde draait, begint
bij een goede container en een geautomatiseerde build.

> Als beheerder wil ik een releaseversie van de UNO-API reproduceerbaar als
> container kunnen starten en controleren, zodat dezelfde applicatie met
> omgevingsconfiguratie naar een volgende omgeving kan worden doorgezet.

De richttijd voor de volledige afname is **30 minuten**. Dit is inclusief het
maken van de opdracht en het aansluitende assessmentgesprek. De algemene
regels staan in de [root-README](../README.md).

Een echte deploymentomgeving is er voor deze opdracht niet. Je richt de
containerconfiguratie en een GitHub Actions-workflow in. De workflow hoeft
tijdens het assessment niet daadwerkelijk te draaien.

## Startsituatie

- `backend/Dockerfile` bouwt en start de API, maar gebruikt Maven ook als
  runtimeomgeving.
- `docker-compose.yml` bevat een PostgreSQL-service en een
  applicatieservice. De API gebruikt nog niet de database uit Compose.
- `.env.example` bevat veilige lokale voorbeeldconfiguratie.
- `.github/workflows/operations.yml` bevat een voorbereide workflow met
  checkout en Java 21.

Werk alleen aan de operationsbestanden. Uitwerkingen van CISQ1,
maintainability of security zijn niet nodig.

## Opdracht

### 1. Maak een multistage image

Wat niet in een productiecontainer zit, kan ook niet stukgaan of lekken.
Verbeter `backend/Dockerfile`:

- gebruik een afzonderlijke buildfase met Java 21 en Maven;
- voer `mvn package` uit zonder tests over te slaan;
- gebruik daarna een Java 21-runtimeimage zonder Maven;
- kopieer alleen het gebouwde JAR-bestand naar de runtimefase;
- start de applicatie met een `ENTRYPOINT` in exec-vorm;
- leg gebruikte basisimages vast met een specifieke tag.

De uiteindelijke image mag geen broncode, Maven-cache of buildtool nodig
hebben om te starten.

### 2. Configureer de lokale runtime

Configuratie die per omgeving verschilt, hoort buiten je code en buiten
Git. Maak lokaal `.env` op basis van `.env.example`; dit bestand blijft
buiten Git.

Vul de `api`-service in `docker-compose.yml` aan:

- geef datasource-URL, gebruikersnaam en wachtwoord via
  omgevingsvariabelen door;
- gebruik `db` als databasehost, niet `localhost`;
- publiceer poort `8080`.

Commit geen echte wachtwoorden, tokens of andere omgevingsgeheimen.

### 3. Richt de buildworkflow in

Een release wil je niet met de hand bouwen; dat werk automatiseer je. Maak
`.github/workflows/operations.yml` af:

- voer `mvn -f backend/pom.xml clean verify` uit;
- bouw de image alleen nadat Maven succesvol is afgerond;
- gebruik `backend` als buildcontext;
- tag de image met `${{ github.sha }}`, zodat iedere commit een herkenbare
  image oplevert.

Je hoeft de workflow niet uit te voeren. Een registry, publicatiestap
en deployment zijn niet nodig. Je hoeft de image niet te bouwen en Compose
niet te starten; dit levert binnen deze opdracht geen extra punten op.
Tijdens het gesprek leg je aan de hand van de workflow uit welke stap bij
een fout stopt en welke image de workflow bouwt.

## Dit lever je op

- Een multistage `backend/Dockerfile` met vastgelegde basisimages.
- Een aangevulde `api`-service in `docker-compose.yml`, met een lokale
  `.env` die buiten Git blijft.
- Een afgemaakte workflow in `.github/workflows/operations.yml`.
