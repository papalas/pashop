# DB
Projekt používá postgresql db, očekává lokálně docker, přes docker-compose a spring-boot-docker-compose si startuje image, image má persistentní volume....
DB je verzována via liquibase, chtěl jsem si to vyzkoušet....
v tuto chvíli vůbec neřeším výkon, asi je tam jen primary key (identita)... počítání v eshopu by mohlo být pro hodně dat složitější, dalo by se naindexovat...

Datainitializer
CommandLineRunner - při spuštění app, naplnění usera když žádný není.
pokud nejsou v db produkty, 100 jich náhodně vyrobí....

-

# vrstvy aplikace
K datům přistupuji přes JPA -- mám data model
    mám model    
    mám interface (repository)
    mám DTO

mám service - kontroluje business logiku
mám controller - mapuje http požadavky....

# auth logika: 
application.properties - user, password to empty
CustomUserDetailsService -- třída pro ověření usera 
SecurityConfig - řeší přístupy....

# Openapi
přes springdoc-openapi-starter-webmvc-ui, přístup přes /api-docs a /swagger-ui.html, generování openapi.json v buildu... 
nutné v buildu definovat, api při testu app běžela... kopíruje přímo z webserveru
nutné anotovat kontrolery... 

# API listProducts: 
používá paging JPA, @RequestParam, search by description a by name, search query jde dělat asi 4 způsoby, používám zatím to nejjednodušší.
asi by se lehce dalo udělat order by, vytvořen Generic PageDTO interface
pro individuální položku @PathVariable



# TODOS::

# background 1 minute task

pak se pustit do api...




