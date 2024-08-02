# DB
Projekt používá postgresql db, očekává lokálně docker, přes docker-compose a spring-boot-docker-compose si startuje image, image má persistentní volume....
DB je verzována via liquibase, chtěl jsem si to vyzkoušet....
v tuto chvíli vůbec neřeším výkon, asi je tam jen primary key (identita)... počítání v eshopu by mohlo být pro hodně dat složitější, dalo by se naindexovat...

# vrstvy aplikace
K datům přistupuji přes JPA -- mám data model
    mám model    
    mám interface (repository)
    mám DTO

mám service - kontroluje business logiku
mám controller - mapuje http požadavky....

# auth logika: 
application.properties - user, password to empty
CustomUserDetailsService -- třída pro ověření usera CommandLineRunner - při spuštění app, naplnění usera když žádný není.
SecurityConfig - řeší přístupy....



# TODOS::
dodělat solení hesla...
vytvořit swagger... springdoc + configure, annotate controllers, openapiconfig class, přidat do maven konfigurece... 

pak se pustit do api...




