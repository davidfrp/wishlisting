# wishlisting
...

## Kørselsvejledning
1. Opret application.properties

### application.properties
Denne fil er undladt fra git (og skal manuelt oprettes), da den indeholder sårbare oplysninger vedrørende etableringen af databaseforbindelsen.
Filen application.properties skal oprettes i mappen /src/main/resources/

For at få programmet til at køre, skal brugernavn, adgangskode og url'en til databasen, udfyldes. Nedenfor ses en tabel over nogle af de forskellige properties som er påkrævet.

Property | Beskrivelse | Valgfrit
--- | --- | ---
spring.datasource.username= | Indsæt brugernavnet for databaseforbindelsen. | Nej
spring.datasource.password= | Indsæt adgangskoden for databaseforbindelsen. | Nej
spring.datasource.url= | Indsæt url'en til databaseforbindelsen. | Nej
spring.mvc.hiddenmethod.filter.enabled=true | Tillader formularer at sende andre foresprøgsler end GET og POST. | Nej
spring.jpa.hibernate.ddl-auto=none | Kan, evt. ved første kørsel eller ved debugging, sættes til `create-drop` for at fjerne og genoprette alle tabellerne ved opstart. | Ja
spring.jpa.show-sql=true | Hvorvidt om kørte queries skal skrives til konsolen. | Ja
spring.jpa.properties.hibernate.format_sql=true | Formaterer de queries som [Hibernate](https://hibernate.org/) skriver til konsolen. | Ja
server.servlet.session.tracking-modes=cookie | Når en session oprettes, bliver id'et ikke vist i url'en. | Ja

`spring.jpa.hibernate.ddl-auto=update` bør være sat under første opstart. Dette vil opsætte databasen så den indeholder de kolonner som modellerne kræver.

### CSS/SCSS
Selvom der benyttes SCSS til styling af applikationen, er CSS filerne inkluderet med projektet.
