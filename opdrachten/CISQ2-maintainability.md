# CISQ2 praktijkassessment: maintainability

In deze opdracht verbeter je de onderhoudbaarheid van een afgebakende
CSV-import voor UNO-spelerprofielen. Code die vandaag werkt, moet morgen
uitbreidbaar zijn; daar draait deze opdracht om.

> Als spelbeheerder wil ik spelerprofielen uit verschillende
> bestandsformaten kunnen importeren, zodat een nieuw formaat kan worden
> toegevoegd zonder de bestaande importfunctionaliteit te wijzigen.

De richttijd voor de volledige afname is **45 minuten**. Dit is inclusief het
maken van de opdracht en het aansluitende assessmentgesprek. De algemene
regels staan in de [root-README](../README.md).

## Startsituatie

Een spelbeheerder importeert spelerprofielen via:

```http
POST /uno/players/imports
Content-Type: text/csv

username,displayName,ready
```

Elke regel bevat precies deze drie waarden, zonder header. Bijvoorbeeld:

```text
carol,Carol,true
dave,Dave,false
```

Het HTTP-gedrag ligt vast:

- een geldige import retourneert `201 Created` met het aantal geïmporteerde
  profielen;
- een regel met een verkeerd aantal velden, een lege gebruikersnaam of
  weergavenaam, of een andere `ready`-waarde dan `true` of `false`
  retourneert `400 Bad Request`;
- bij een ongeldige regel verandert geen enkel profiel;
- een gebruikersnaam die al bestaat retourneert `409 Conflict`.

De bestaande functionaliteit werkt, maar de importcode mengt
verantwoordelijkheden die beter gescheiden kunnen worden. De startsituatie
bevat volledige regressietests voor het beschreven gedrag en een nog
uitgeschakelde startplaats voor architectuurtests.

## Opdracht

### 1. Onderzoek

Refactoren begint met weten wat je precies wilt oplossen. Maak
`maintainability-notes.md` in de repository-root. Leg daarin kort één
concreet maintainability-risico in de code voor de spelerprofiel-import
vast, wat je ermee doet en hoe je de uitkomst controleert.

### 2. Refactor de spelerprofiel-import

Behoud het vastgelegde HTTP-gedrag en refactor de code zodat:

- de controller alleen met de applicatielaag samenwerkt;
- CSV-specifieke parsing en validatie niet in controller of domein staan;
- de import-use case een abstractie gebruikt waarvan CSV een implementatie
  is;
- een XML-implementatie later kan worden toegevoegd zonder wijziging van de
  import-use case;
- de bestaande atomiciteit bij een mislukte import behouden blijft.

Kies zelf passende namen, packages en klassen. Voorkom onnodige
abstracties.

### 3. Regressietests en architectuurcontrole

De meegeleverde regressietests zijn je vangnet tijdens het refactoren. Voer
ze vóór en na de refactor uit en houd ze ongewijzigd en groen: zij bewaken
geldige invoer, ongeldige invoer, conflicten en atomiciteit.

Voeg daarnaast één ArchUnit-regel toe voor een relevante laaggrens en laat
deze meedraaien in `mvn verify`. De meegeleverde
architectuurtest is bewust uitgeschakeld totdat jij die regel hebt
gemaakt.

### 4. Boven niveau

Wil je meer laten zien? Voeg PMD als Maven-check toe voor de code van de
spelerprofiel-import, met een kleine regelset die je kunt uitleggen. Deze
check vult je tests en ArchUnit-regel aan en vervangt ze niet.

Kaartspelregels, authenticatie, CI/CD en deployment vallen buiten deze
opdracht.

## Dit lever je op

- `maintainability-notes.md` met één risico, je aanpak en je controle.
- Een gerefactorde spelerprofiel-import met ongewijzigde, groene
  regressietests.
- Eén relevante ArchUnit-regel die meedraait in `mvn verify`.
- Optioneel: een uitlegbare PMD-check voor de importcode.

## Uitvoeren

Voer vanuit `backend/` uit:

```
mvn test
mvn verify
```
