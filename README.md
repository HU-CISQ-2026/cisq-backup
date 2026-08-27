# CISQ praktijkassessment

Welkom! Deze repository is het startpunt voor vier onafhankelijke
praktijkassessments. Je werkt alleen aan de opdracht die je van de docent
krijgt; wijzigingen voor een andere opdracht horen niet bij jouw uitwerking.

De backend is een Java 21 / Spring Boot-applicatie voor het kaartspel UNO.
Spelers hebben kaarten in hun hand en spelen die volgens de spelregels. De
applicatie bevat alleen de functionaliteit die voor de assessments nodig is.
Je hoeft dus geen volledig spel of frontend te maken.

## Zo werk je

- Lees de opdracht in `opdrachten/` die bij jouw assessment hoort.
- Tijdens het maken mag je alle hulpmiddelen gebruiken, ook generatieve AI.
- Werk zorgvuldig: behoud bestaand gedrag, houd de relevante controles groen
  en maak betekenisvolle commits. Een vaste commitfrequentie is er niet.
- Ben je klaar met de opdracht? Lever dan eerst je Git-repository in bij de
  bijbehorende opdracht in de Canvasomgeving. Daarna kom je bij een docent
  langs voor een kort individueel gesprek. Het project mag openstaan, maar
  AI en zoekmachines gebruik je dan niet. Je licht je eigen code, tests en
  keuzes toe.
- Begin iedere opdracht op een schone Git-branch vanaf het oorspronkelijke
  template-startpunt. De opdrachten zijn onafhankelijk: gebruik geen
  oplossing of artefact uit een andere opdracht.

## Opdrachten

De richttijd omvat de volledige afname: het maken van de opdracht en het
aansluitende assessmentgesprek.

| Opdracht | Richttijd volledige afname | Status |
|---|---:|---|
| [CISQ1: kaart spelen](opdrachten/CISQ1.md) | 60 minuten | Beschikbaar |
| [CISQ2: maintainability](opdrachten/CISQ2-maintainability.md) | 45 minuten | Beschikbaar |
| [CISQ2: security](opdrachten/CISQ2-security.md) | 45 minuten | Beschikbaar |
| [CISQ2: operations](opdrachten/CISQ2-operations.md) | 30 minuten | Beschikbaar |

## Uitvoeren

Voer Maven-commando's uit vanuit `backend/`:

```
mvn test
```

In de opdracht zelf vind je eventuele aanvullende commando's en
kwaliteitseisen.
