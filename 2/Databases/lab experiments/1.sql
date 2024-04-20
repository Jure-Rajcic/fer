-- Id: 47275
-- Zadatak 1. (4 boda)

-- Pomoću jedne SQL naredbe ispišite naslov filma i datum izdavanja u obliku "naslov filma (released: dd.mm.yyyy)", trajanje filma, ocjenu i opis duljine filma definiran na sljedeći način:

-- short ako film traje kraće od 1 sat
-- medium ako film traje između 1 sat i 1 sat i 45 minuta
-- long ako film traje dulje od 1 sat i 45 minuta
-- Ispisati samo filmove koji imaju prethodnike, ocjenu veću od 50 i NE sadrže dvotočku u naslovu.
-- Stupac s naslovom filma i datumom izdavanja nazovite movieReleased, a stupac s opisom duljine filma movieLength.
-- Rezultate poredajte po opisu duljine trajanja filma abecedno.
SELECT tracktitle || ' (released: ' || releaseDate || ')' AS movieReleased, duration, trackrating,
       CASE 
          WHEN duration < '1 hour' THEN 'short'
          WHEN duration BETWEEN '1 hour' AND 
                 '1 hour 45 minutes' THEN 'medium'
          WHEN duration > '1 hour 45 minutes' THEN 'long'
       END as movieLength
FROM track NATURAL JOIN movie 
WHERE trackrating > 50
  AND tracktitle NOT LIKE '%:%'
  AND prevmovieid IS NOT NULL
ORDER BY movielength


-- Id: 47277
-- Zadatak 2. (5 bodova)

-- Ovaj zadatak riješite pomoću jedne SQL naredbe.
-- Za epizode serija čiji je redni broj u sezoni veći od 10 te pripadaju 5. sezoni nadalje i koje u nazivu sadrže barem jedan od interpunkcijskih znakova - točka, uskličnik ili upitnik, ispišite naziv epizode serije, sezonu serije kojoj ta epizoda pripada te redni broj epizode u toj sezoni.
-- Također, ispišite identifikator korisničkog računa i nazive profila s kojih je, u mjesecu jednakom tekućem mjesecu (bez obzira na godinu), započeto gledanje te epizode serije, a epizoda serije je odgledana barem 30 minuta. Uz navedene atribute potrebno je ispisati i koliko je epizoda odgledana (do kuda se došlo s gledanjem epizode u odnosu na početak epizode).
-- Pod „odgledan barem 30 minuta“ misli se na to da je osoba koja gleda epizodu serije pogledala barem 30 minuta epizode u odnosu na početak epizode - ta informacija je za svako gledanje zapisana u atributu savedProgress.

-- Za epizode serija koje u nazivu sadrže barem jedan od prethodno spomenutih interpunkcijskih znakova, čiji je redni broj u sezoni veći od 10 te pripadaju 5. sezoni nadalje, a nitko ih nije započeo gledati u tekućem mjesecu (bez obzira na godinu) niti su odgledane barem 30 minuta, za identifikator korisničkog računa, naziv profila te koliko je epizoda odgledana ispišite NULL vrijednosti.

-- Upit mora ispravno raditi bez obzira na vrijeme pokretanja.
SELECT trackTitle, seasonNo, episodeNo, 
                ownerId, profileName, savedProgress
  FROM track
       NATURAL JOIN showEp
       LEFT JOIN trackview ON trackView.trackId = track.trackId
       AND EXTRACT(MONTH FROM viewstartdatetime) =
           EXTRACT(MONTH FROM CURRENT_TIMESTAMP)
       AND savedProgress >= '30 min':: INTERVAL
 
where seasonNo > 4
AND episodeNo > 10
AND (trackTitle LIKE '%.%' OR  trackTitle LIKE '%?%' OR trackTitle LIKE '%!%')

-- Id: 47281
-- Zadatak 3. (6 bodova)
-- Pomoću jedne SQL naredbe za sve epizode serija koje imaju hrvatske podnaslove (ime jezika podnaslova 'Croatian') s ocjenom serije preko 80, a koje su gledane barem jednom ali nikad vikendom (obzirom na trenutak početka gledanja) ispišite naziv serije, naziv epizode i ocjenu serije.

-- Ispis poredati po ocjeni serije silazno, potom po nazivu serije abecedno i nazivu epizode abecedno.
-- Pobrinite se da se u rezultatu ne pojavljuju duple ntorke.
SELECT DISTINCT showtitle, tracktitle, showrating 
  FROM show  
       NATURAL JOIN showep NATURAL JOIN track NATURAL JOIN subtitle  NATURAL JOIN trackview 
       JOIN language ON language.langid  = subtitle.subtitlelangid 
 WHERE langName = 'Croatian'  
   AND showrating > 80 
   AND track.trackid NOT IN  

    (SELECT trackid 
       FROM trackview 
      WHERE (EXTRACT(DOW FROM viewstartdatetime) IN (0,6))) 

ORDER BY showrating DESC, showtitle, tracktitle; 

