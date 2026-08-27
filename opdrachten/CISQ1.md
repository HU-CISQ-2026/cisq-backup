# CISQ1 praktijkassessment: kaart spelen

In deze opdracht verbeter je de softwarekwaliteit van een afgebakend deel van
de UNO-backend. Goede tests geven een team het vertrouwen om code te blijven
veranderen; dat vertrouwen bouw jij hier op.

De codebase bevat al een werkende basis voor deze user story:

> Als huidige speler wil ik een geldige getalkaart spelen, zodat de kaart op
> de aflegstapel komt en de volgende speler aan de beurt is.

De richttijd voor de volledige afname is **60 minuten**. Dit is inclusief het
maken van de opdracht en het aansluitende assessmentgesprek. De algemene
regels staan in de [root-README](../README.md).

## Startsituatie

De bestaande code implementeert de use case via:

```http
POST /uno/games/{gameId}/plays
X-User: <gebruikersnaam>
Content-Type: application/json

{"card":"<kaartcode>"}
```

Gebruik spel `assessment-game`. De huidige speler is `alice`; haar hand
bevat `R7`, `B1` en `Y2`. De bovenste kaart is `R1`.

De API reageert zo:

- succes retourneert `204 No Content`;
- een onbekend spel retourneert `404 Not Found`;
- een ontbrekende, ongeldige, niet-passende of niet-toegestane kaart
  retourneert `400 Bad Request`.

Alleen getalkaarten (`0` tot en met `9`) vallen binnen deze user story.

De basistests zijn groen. Tests met lege of TODO-assertions tellen pas mee
nadat je ze betekenisvol hebt aangevuld.

## Opdracht

### 1. Unit tests voor `Game`

De spelregels leven in het domein, dus daar begint je testwerk. Schrijf
betekenisvolle unit tests voor `Game` en bereik minimaal **85% branch
coverage**. Test in ieder geval:

- een speler die niet aan de beurt is;
- een kaart die niet in de hand zit;
- een niet-passende kaart;
- een actiekaart.

`GameTest` bevat een gedeelde beginsituatie met `@BeforeEach` en een
voorbereide parameterized test. Werk die test uit zodat dezelfde uitvoering
en assertions zowel een kaart met een passende kleur als een kaart met een
passende waarde controleren.

### 2. Een kaart trekken met TDD

Nieuwe spelregels voeg je testgedreven toe: zo weet je zeker dat iedere
regel door een test wordt afgedwongen. Voeg met TDD `draw(String username)`
toe aan `Game`:

- alleen de huidige speler zonder passende getalkaart trekt precies één
  kaart;
- past de getrokken kaart, dan mag dezelfde speler hem spelen;
- past de kaart niet, dan eindigt de beurt en is de volgende speler aan zet;
- bij een leeg deck volgt een passende foutmelding.

De getrokken kaart blijft in de hand van de speler.
Je bepaalt in je test zelf aan welke kant van de lijst de bovenste kaart van
het deck ligt. Maak die keuze herkenbaar in de testopstelling.

Schrijf voor iedere genoemde regel eerst een test die om de juiste reden
faalt. Maak minimaal één volledige TDD-cyclus zichtbaar in je
commitgeschiedenis:

1. commit de falende test vóór de bijbehorende productiecode;
2. implementeer precies genoeg code om de test te laten slagen;
3. verbeter daarna zo nodig de code terwijl alle tests groen blijven.

Test naast de drie trekuitkomsten ook dat een speler niet mag trekken
wanneer die niet aan de beurt is, nog een passende getalkaart heeft of het
deck leeg is. Aan het einde slagen alle tests voor het trekken van een
kaart.

### 3. Integratietests voor `PlayerController`

Of de API zich echt goed gedraagt, zie je pas bij een volledig request.
Maak de meegeleverde `MockMvc`-integratietests voor `PlayerController`
compleet. De Spring-configuratie,
requests, requestbodies en testdata staan al klaar in
`PlayerControllerIntegrationTest`. Jij voegt betekenisvolle assertions toe
voor de HTTP-status en, waar relevant, de responsebody.

Een speler heeft een unieke `username`, een `displayName` en een
`ready`-status.

### 4. Boven niveau

Wil je meer laten zien? Kies één van deze uitbreidingen:

- voeg een uitvoerbaar Gherkin-scenario voor *kaart spelen* toe;
- of gebruik PITest voor `Game` en behaal minimaal **70% mutation
  coverage**.

Persistentie, actiekaarten, frontend en het starten van nieuwe spellen
vallen buiten deze opdracht.

## Dit lever je op

- Aangevulde unit tests in `GameTest`, inclusief de uitgewerkte
  parameterized test.
- `draw` in `Game`, met tests en een zichtbare TDD-cyclus in je commits.
- Aangevulde assertions in `PlayerControllerIntegrationTest`.
- Optioneel: een Gherkin-scenario of een PITest-resultaat.

## Uitvoeren

Voer vanuit `backend/` uit:

```
mvn test
mvn -Pcisq1 verify
```

Alleen voor de optionele PITest-uitbreiding:

```
mvn pitest:mutationCoverage
```
