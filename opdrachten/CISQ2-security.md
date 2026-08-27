# CISQ2 praktijkassessment: security

In deze opdracht verbeter je de beveiliging van een endpoint waarmee
spelers hun persoonlijke UNO-handkaarten bekijken. Een werkend succespad is
niet genoeg: een speler moet erop kunnen vertrouwen dat niemand anders zijn
kaarten ziet.

> Als speler wil ik dat alleen ik mijn eigen handkaarten kan bekijken nadat
> mijn identiteit is gecontroleerd, zodat andere spelers mijn kaarten niet
> kunnen inzien.

De richttijd voor de volledige afname is **45 minuten**. Dit is inclusief het
maken van de opdracht en het aansluitende assessmentgesprek. De algemene
regels staan in de [root-README](../README.md).

## Startsituatie

Een speler vraagt handkaarten op via:

```http
GET /uno/games/{gameId}/players/{username}/hand
Authorization: ******
```

Gebruik het voorbereide spel `assessment-game`. Alice heeft de kaarten
`R7`, `B1` en `Y2`; Bob heeft `G5`.

De bestaande functionaliteit accepteert een JWT en retourneert de gevraagde
hand. Het succespad werkt, maar de beveiliging voldoet nog niet aan de user
story:

- de applicatie leest de identiteit uit het JWT zonder de handtekening en
  verloopdatum betrouwbaar te controleren;
- een ingelogde speler kan door de gebruikersnaam in het pad te veranderen
  de hand van een andere speler opvragen.

De `X-User`-header die elders in de template als ontwikkelmock wordt
gebruikt, hoort niet bij dit endpoint en valt buiten deze opdracht.

De startsituatie bevat een groene succes- en foutafhandelingstest. Voor de
overige securitytests staan de requests, tokens en testdata al klaar. Deze
tests tellen pas mee nadat je betekenisvolle assertions hebt toegevoegd.

## Gewenst beveiligingsgedrag

- Een geldig, ondertekend en niet-verlopen JWT identificeert de speler via
  de `sub`-claim.
- Alleen die speler mag de eigen hand opvragen.
- Een ontbrekend, gewijzigd, ongeldig ondertekend of verlopen token
  retourneert `401 Unauthorized`.
- Een geldige speler die de hand van een andere speler opvraagt ontvangt
  `403 Forbidden`.
- Een onbekend spel retourneert `404 Not Found` zonder stacktrace of
  interne exceptiemelding.
- De server baseert de autorisatie op het gevalideerde JWT en niet op een
  losse gebruikers- of rolheader.

## Opdracht

### 1. Onderzoek de risico's

Een goede fix begint met begrijpen wat er mis kan gaan. Maak
`security-notes.md` in de repository-root en beschrijf voor beide genoemde
problemen:

- waar het probleem in de code voorkomt;
- het effect op vertrouwelijkheid of integriteit;
- welke maatregel je toepast;
- hoe je controleert dat de maatregel werkt;
- welke relevante beperking daarna overblijft.

Houd de analyse kort en koppel haar aan je eigen code en tests.

### 2. Verbeter de JWT-validatie

Pas de tokenverwerking aan zodat de server de cryptografische handtekening
en de verloopdatum controleert voordat de `sub`-claim wordt vertrouwd. De
benodigde JJWT-dependencies zijn aanwezig.

Lees het lokale assessmentgeheim uit configuratie. Plaats geen geheim als
literal in Java-code en neem geen echt wachtwoord, token of
productiegeheim op in de repository.

### 3. Dwing autorisatie af

Weten wie iemand is, is niet hetzelfde als weten wat diegene mag.
Controleer server-side dat de gevalideerde `sub`-claim overeenkomt met de
speler in het requestpad. Geef een geauthenticeerde speler zonder toegang
`403 Forbidden`. Houd dit onderscheid met een ongeldige authenticatie
(`401 Unauthorized`) zichtbaar in de implementatie en tests.

### 4. Maak de securitytests compleet

Vul in `HandControllerSecurityIntegrationTest` de ontbrekende
statusassertions aan. De requests, tokenvarianten en testdata zijn al
voorbereid. De tests controleren:

- toegang tot de eigen hand met een geldig token;
- een ontbrekend token;
- een token waarvan inhoud of handtekening is gewijzigd;
- een verlopen token;
- toegang tot de hand van een andere speler;
- een onbekend spel zonder technische foutinformatie in de response.

Alle tests moeten deterministisch en onafhankelijk van externe systemen
zijn.

### 5. Boven niveau

Wil je meer laten zien? Voeg een verwachte issuer (`iss`) en audience
(`aud`) toe aan het tokencontract. Valideer beide server-side en schrijf
tests voor een token met een verkeerde issuer of audience.

Registratie, wachtwoordopslag, rollenbeheer, refresh tokens, logout,
dependency scanning, CI/CD en containers vallen buiten deze opdracht.

## Dit lever je op

- `security-notes.md` met beide risico's, je maatregelen en je controles.
- JWT-validatie die handtekening en verloopdatum controleert.
- Server-side autorisatie met zichtbaar onderscheid tussen
  `401 Unauthorized` en `403 Forbidden`.
- Aangevulde statusassertions in `HandControllerSecurityIntegrationTest`.
- Optioneel: issuer- en audiencevalidatie met tests.

## Uitvoeren

Voer vanuit `backend/` uit:

```
mvn -Dtest=HandControllerSecurityIntegrationTest test
mvn test
```
