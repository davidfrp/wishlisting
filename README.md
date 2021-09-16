# Wishlisting

## Kørselsvejledning
1. Opsæt databasen ved at køre `docker-compose up` fra mappen 'wishlisting'
2. Docker containeren burde nu være oprettet og kørende.

### application.properties

Property | Beskrivelse | Valgfrit
--- | --- | ---
spring.datasource.username= | Brugernavnet for databaseforbindelsen. | Nej
spring.datasource.password= | Adgangskoden for databaseforbindelsen. | Nej
spring.datasource.url= | Url'en til databaseforbindelsen. | Nej
spring.mvc.hiddenmethod.filter.enabled=true | Tillader formularer at sende andre foresprøgsler end GET og POST. | Nej
spring.jpa.hibernate.ddl-auto=none | Kan, evt. ved debugging, sættes til `create-drop` for at fjerne og genoprette alle tabellerne ved opstart. | Ja
spring.jpa.show-sql=true | Hvorvidt om kørte queries skal skrives til konsolen. | Ja
spring.jpa.properties.hibernate.format_sql=true | Formaterer de queries som [Hibernate](https://hibernate.org/) skriver til konsolen. | Ja
server.servlet.session.tracking-modes=cookie | Når en session oprettes, bliver id'et ikke vist i url'en. | Ja

`spring.jpa.hibernate.ddl-auto=update` bør være sat under første opstart. Dette vil opsætte databasen så den indeholder de kolonner som modellerne kræver. Derefter kan værdien sætte til 'none' eller 'validate' hvis man ikke ønsker at databasen skal tilpasse sig koden.

### CSS/SCSS
Selvom der benyttes SCSS til styling af applikationen, er CSS filerne inkluderet med projektet.
For selv at style SCSS yderligere, kræves det at man installere SASS fra https://sass-lang.com/ og at man opsætter en rutine, der kan kompilere SCSS til CSS.  
