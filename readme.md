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

# Logika objednávek:
Objednávky jsou uloženy v tabulce orders, objednávka má tři možné stavy: BASKET, ORDERED, DELIVERED, dále má odpočet days of delivery, podle kterého měním stav z ORDERED na DELIVERED.
Skladová dostupnost se finálně počítá až při objednávání, přidání do košíku ještě není závazné. Vůbec je logika eshopu hodně zjednodušená, chápu úkol jako intro to spring boot.
Order_items- obsahuje list položek.

# Api AddItemToBasket
vše podle přihlášeného usera...najdu nebo vytvořím košík, potom v košíku najdu nebo vyrobím položku a přičtu objednávaný počet. přepočítám total položky a uložím
ošetřená logika v try bloku, pokud nenajde usera nebo produkt, vrátí http chybu
namapováno jako post operace, možná by bylo lepší jak put a delete
note: Možná by bylo dobré, aby api na konci vracelo současný stav košíku? líp by se na FE pracovalo...

# Api RemoveItemFromBasket
Logika pokud je víc produktů k remove než je v koši? Zatím maže

# Api ListBasket
Vyrobeny DTO, aby s tím šlo pracovat, nejsem si jist, jestli model překládat na dto v controlleru, nebo v service....  
zobrazuje košík, případně not found nebo 500...

# List orders
Zkouším si enum na orderstatus.
vrací list, může být prázdné

# API pay basket
logika na počet zboží na skladě...
přehodí objednávku do stavu ORDERED a nastaví datum objednávky.

# background 1 minute task
@EnableScheduling, @Scheduled cron
logika projde objednávky ve stavu ordered, odečítá days to deliver a pokud o, dodá a nstaví delivery date.

# TODOS::

