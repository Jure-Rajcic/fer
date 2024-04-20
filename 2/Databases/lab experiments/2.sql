-- Id: 47556
-- Zadatak 1. (4 boda)

-- Pomoću jedne SQL naredbe ispišite ime i prezime vlasnika korisničkog računa, naziv serije i naziv epizode, broj gledanja epizode od strane tog korisničkog računa bez obzira na duljinu gledanja (noOfViews), te duljinu neodgledanog dijela epizode(leftToWatch) i to za ono gledanje tijekom kojeg je korisnik postigao najveću udaljenost od početka epizode (to je zapisano u trackView.savedProgress). U obzir uzeti samo epizode:
-- koje je pojedini vlasnik korisničkog računa gledao više od četiri puta
-- čiji je najdulje gledani interval za pojedinog vlasnika veći od 95% duljine trajanja epizode

-- Primjera radi (identičan primjer je u tablici) - ako je profil pogledao epizodu serije Cheers s nazivom Baby Balk (koja traje 28 minuta) više od četiri puta i imao je pritom intervale trajanja gledanja 23 minute i 54 sekunde, 18 minuta i 33 sekunde, 7 minuta i 14 sekundi, 27 minuta i 22 sekunde te 22 minute i 42 sekunde, potrebno je ispisati 38 seconds kao preostalo trajanje gledanja do potpunog.

-- owner	episode	noofviews	lefttowatch
-- Humzah John	Cheers (Baby Balk)	5	38 seconds
-- Bertram Robbins	Derek (Episode 6)	5	58 seconds

-- Rezultate poredajte prema nazivu serije i nazivu epizode (oboje uzlazno).
SELECT owner.firstname || ' ' || owner.lastname as owner,
       show.showtitle || ' (' || track.tracktitle || ')' as episode, 
       COUNT(*) noOfViews,   
       duration-MAX(savedprogress) as lefttowatch  
  FROM trackView  
       NATURAL JOIN owner   
       NATURAL JOIN track  
       NATURAL JOIN showEp 
       NATURAL JOIN show 
GROUP BY owner.firstname, owner.lastname, show.showtitle, track.tracktitle, track.duration 
HAVING MAX(savedprogress)> duration*0.95  
      AND count(*)>4  
order by showtitle, track.tracktitle 

-- Id: 47528
-- Zadatak 2. (5 bodova)

-- Za medijske sadržaje postoji ograničenje ovisno o starosnoj dobi (track.tAgeRestriction). Ako je ograničenje nepoznato, smatra se da medijski sadržaj nema ograničenje ovisno o starosnoj dobi.
-- Gledatelj iza pojedinog korisničkog profila može ocijeniti sviđa li mu se sadržaj ili ne (profileTrack.liked poprima vrijednost 1 ili –1), no sadržaj može ostati i neocijenjen (ima vrijednost NULL).
-- Vlasnici streaming platforme žele uvesti dodatne razine kontrole i konzistencije podataka.

-- a) Jednom SQL naredbom osigurajte da se ograničenje za medijske sadržaje ovisno o starosnoj dobi ne može postaviti iznad 21 godine.
ALTER TABLE track 
ADD CONSTRAINT chkAgeRestr 
CHECK (tAgeRestriction <=21 OR tAgeRestriction IS NULL) 

-- b) Napišite niz SQL naredbi za kreiranje svih potrebnih objekata, koji će spriječiti da korisnik ocijeni sviđa mu li se određeni sadržaj, ako nije odgledao barem četvrtinu tog sadržaja.
CREATE FUNCTION chkProgressLiked() RETURNS TRIGGER AS $x$  

  DECLARE trackDuration 	track.duration%TYPE; 
  DECLARE profileTrackProgress  trackview.savedProgress%TYPE; 
  DECLARE p_trackTitle 		track.trackTitle%TYPE; 

  BEGIN 
    SELECT duration, trackTitle  
    INTO trackDuration, p_trackTitle 
    FROM track 
    WHERE trackId = NEW.trackId; 

    SELECT MAX(savedProgress) 
    INTO profileTrackProgress 
    FROM profileTrack NATURAL JOIN trackVIEW 
    WHERE trackId = NEW.trackId
    AND ownerId = NEW.ownerId
    AND profileName = NEW.profileName; 

    IF (profileTrackProgress < 0.25 * trackDuration) THEN	 
  RAISE EXCEPTION 'Pogreška: Niste odgledali barem četvrtinu sadržaja % % te još ne možete ocijeniti sadržaj.',  NEW.trackId, p_trackTitle; 
   END IF; 
   
  RETURN NEW; 
  END; 
$x$ LANGUAGE plpgsql; 

--DROP TRIGGER updLikedProfileTrack 
CREATE TRIGGER updLikedProfileTrack 
BEFORE UPDATE OF liked ON profileTrack 
FOR EACH ROW 
WHEN (NEW.liked <> OLD.liked) 
EXECUTE FUNCTION chkProgressLiked(); 

-- Id: 47529
-- Zadatak 3. (4 bodova)

-- Administrator baze podataka Streamflix želi stvoriti novu ulogu MOVIE_CLEANER koja će obavljati sljedeće poslove: - pregled svih stupaca iz tablice TRACK - ažuriranje svih neključnih atributa iz tablice TRACK osim tipa sadržaja - brisanje redaka iz tablice TRACK
-- pri čemu se navedeni poslovi obavljaju isključivo nad retcima iz tablice TRACK vezanim uz filmske sadržaje koje je u zadnje dvije godine gledalo manje od pet različitih korisnika (profila).

-- Napišite niz naredbi kojima će administrator ovo omogućiti te potom ulogu dodati postojećem korisniku naziva PERO. Pretpostavite sljedeće: - tablica TRACK nalazi se u PUBLIC shemi baze podataka STREAMFLIX - vremenski interval provjere broja gledanja kao referentne trenutke uzima trenutak izvođenja upita i trenutak početka gledanja - uloga MOVIE_CLEANER u svojim upitima će za navedene poslove koristiti virtualnu tablicu naziva TRACK_CLEANER - brisanjem sadržaja redaka tablice TRACK provesti će se kaskadno brisanje redaka koji referenciraju navedene retke - korisnik PERO postoji u sustavu, ali nema mogućnost spajanja na navedenu bazu niti pristup navedenoj shemi

-- NAPOMENA: U zadatku se ne smiju koristiti procedure i okidači.

CREATE VIEW TRACK_CLEANER AS
    SELECT * 
        FROM TRACK 
        WHERE trackid NOT IN (
            SELECT DISTINCT trackid 
                FROM trackview
       			WHERE viewStartDateTime >= 
                    CURRENT_TIMESTAMP - '2 YEARS'::INTERVAL
       			GROUP BY trackid
       			HAVING COUNT (DISTINCT profilename)>= 5
  ) AND trackid IN (SELECT trackid FROM movie)
    -- ili tracktype LIKE 'M'
WITH CHECK OPTION;

CREATE ROLE MOVIE_CLEANER;

GRANT CONNECT ON database STREAMFLIX TO MOVIE_CLEANER;
GRANT USAGE ON schema PUBLIC TO MOVIE_CLEANER;

GRANT SELECT ON TRACK_CLEANER TO MOVIE_CLEANER;
GRANT UPDATE 
   (trackTitle, releaseDate, duration, tAgeRestriction, trackRating) ON TRACK_CLEANER TO MOVIE_CLEANER;
GRANT DELETE ON TRACK_CLEANER TO MOVIE_CLEANER;

GRANT MOVIE_CLEANER TO PERO; 
