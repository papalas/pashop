Projekt používá postgresql db, očekává lokálně docker, přes docker-compose a spring-boot-docker-compose si startuje image, image má persistentní volume....
DB je verzována via liquibase, chtěl jsem si to vyzkoušet....

K datům přistupuji přes JPA -- mám data model
    mám model    
    mám interface (repository)
    mám DTO

mám service - kontroluje business logiku
mám controller - mapuje http požadavky....


TODOS::
udělej auth logiku...

na co je repository? -- slouží pro přístup do db
vytvořit swagger...(flyway)




