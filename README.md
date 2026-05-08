# Ghost Net Fishing

Web-App für die Fallstudie im Kurs IPWA02-01 (IU).
Mit der App können herrenlose Fischernetze (Geisternetze) gemeldet
und Bergungen koordiniert werden.

## Voraussetzungen

- Java 17 oder höher
- IntelliJ IDEA
- Browser

## Starten

1. Projekt in IntelliJ öffnen.
2. Warten, bis Maven die Bibliotheken geladen hat.
3. `GhostnetApplication.java` starten (Run).
4. Im Browser `http://localhost:8080` öffnen.

## Beispieldaten

Beim ersten Start werden 5 Geisternetze und 4 Personen automatisch angelegt.
Die Daten landen unter `data/ghostnet.mv.db` und bleiben nach Neustart erhalten.

Wenn die Daten neu erzeugt werden sollen: Ordner `data` löschen und neu starten.

## H2-Konsole

Solange die App läuft, erreichbar unter `http://localhost:8080/h2-console`.

- JDBC URL: `jdbc:h2:file:./data/ghostnet;AUTO_SERVER=TRUE`
- User: `sa`
- Passwort: leer

`AUTO_SERVER=TRUE` erlaubt parallele Verbindungen, sonst meldet H2
"Database may be already in use".

## Seiten

- `/` — Startseite
- `/netze` — Liste aller Netze
- `/netze?filter=offen` — Liste der noch zu bergenden Netze
- `/netze/neu` — Formular: neues Netz melden
- `/netze/{id}/bergen` — Formular: für die Bergung eintragen
- `/netze/{id}/geborgen` — Aktion: als geborgen markieren
- `/netze/{id}/verschollen` — Formular: als verschollen melden

## Umgesetzte User Stories (5 von 7)

1. MUST: Geisternetze (anonym) erfassen können.
2. MUST: Für die Bergung eines Netzes eintragen können.
3. MUST: Sehen, welche Netze noch zu bergen sind.
4. MUST: Geisternetze als geborgen melden können.
5. COULD #7: Geisternetze als verschollen melden können.

Nicht umgesetzt:

- COULD #5: Weltkarte
- COULD #6: Wer welche Netze bergen möchte

## Technologie

- Java 17, Maven
- Spring Boot 3.2 (Web, Data JPA, Thymeleaf, Validation)
- Hibernate
- H2-Datenbank (Datei)
- Bootstrap 5 + Bootstrap Icons (CDN)

## Projekt-Struktur

```
src/main/java/de/iu/ghostnet/
  GhostnetApplication.java   -> Startklasse
  Beispieldaten.java         -> Beispieldaten beim ersten Start
  controller/                -> HTTP-Anfragen
  service/                   -> Geschäftslogik
  repository/                -> Datenbank-Zugriff
  model/                     -> Entities und Enum
  form/                      -> Form-Objekte für die Formulare

src/main/resources/
  application.properties     -> Konfiguration
  static/css/app.css         -> eigenes Stylesheet
  templates/                 -> HTML-Seiten (Thymeleaf)
  templates/fragments/       -> Navbar und Footer
```
