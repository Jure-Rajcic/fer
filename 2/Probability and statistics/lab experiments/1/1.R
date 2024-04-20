#Dobrodošli u uvod u R! 
#Cilj ovog labosa je upoznati se s osnovnim naredbama jezika R i
#primijeniti naučeno na neke primjere iz vjerojatnosti.
#Skriptu možete pokrenuti online u RStudio Cloudu, za offline pokretanje instalirajte RStudio lokalno
#Pokretati možete liniju po liniju (u RStudio Cloudu s ctrl + Enter)
#ili više linija odjednom ako se mišem označe linije koje se žele pokrenuti i onda pritisne ctrl + Enter


#Definiranje varijabli
#nije potrebno navoditi tip
#R studio automatski ispisuje sve varijable, za ponovni ispis samo treba upisati ime varijable
a = 5
b = 0.8
c = "string"
a

#Definiranje vektora
#koristi se funkcija c() (collection)
vektor1 = c(1, 2, 3)
vektor2 = c(4, 5 ,6)
#ako su vektori jednake dužine mogu se s njima raditi osnovne operacije:
vektor1 + vektor2
vektor1 - vektor2
vektor1 * vektor2
vektor1 / vektor2

#ukoliko u vektoru imamo ponvaljanja, možemo ga definirati i na drugi način:
vektor1 = rep( c(0, 1), c(5, 10)) #napravi vektor koji ima elemente 5 nula i 10 jedinica
vektor1
vektor2 = rep( c("c", "b", "p"), c(2, 3, 4))
vektor2

#iteriranje kroz vektore, suma vektora
sum = 0
for(i in 1:length(vektor1) ) {
  sum = sum + vektor1[i]
}
sum
#Zadatak 1: napišite kod koji će odrediti sumu prvih 10 kvadrata prirodnih brojeva i ispišite rezultat.

#Na ovim labosima riješit ćemo nekoliko zadataka pomoću simulacija u R-u, oslanjajući se pritom na jaki
#zzakon velikih brojeva. Većinu zadatak ste vidjeli na vježbama, tako da se za točna rješenja možete referirati
#u tim materijalima

#Zadatak 2: Iz kutije u kojoj se nalazi 5 crnih, 6 bijelih i 7 zelenih kuglica izvlačimo na sreću 4 kuglice. Odredite
#vjerojatnost da među izvučenim kuglicama: (a) nema crnih, (b) nisu zastupljene sve boje.

#Rješenje: Napravit ćemo simulaciju tako da ćemo definirati naš pokus i onda ga ponavljati 
#puno puta te će procjena tražene vjerojatnosti biti broj_pozitivnih_ishod/ukupan_broj_pokusa

set.seed(1518141) #seed je broj koji označava random stanje, ukoliko seed fiksiramo, na svakom računalu
#random operacije dat će isti rezultat, što je jako bitno kod reproduciranja rezultata
kutija = rep(c("c", "b", "z"), c(5, 6, 7))
broj_ponavljanja = 1e+05
nema_crnih = 0

nisu_zastupljene_sve_boje = 0
for (i in 1:broj_ponavljanja) {
  uzorak = sample(kutija, size = 4, replace = FALSE)
  nema_crnih = nema_crnih + (!is.element("c", uzorak))
  nisu_zastupljene_sve_boje = nisu_zastupljene_sve_boje + (!(is.element("c",
        uzorak) & is.element("b", uzorak) & is.element("z", uzorak)))
}
a_dio_sim = nema_crnih/broj_ponavljanja
b_dio_sim = nisu_zastupljene_sve_boje/broj_ponavljanja
# Egzaktno rjesenje
a_dio_egz = choose(13, 4)/choose(18, 4)
b_dio_egz = 1 - (choose(5, 2) * choose(6, 1) * choose(7, 1))/choose(18, 4) -
                (choose(5, 1) * choose(6, 2) * choose(7, 1))/choose(18, 4) -
                (choose(5, 1) * choose(6, 1) * choose(7, 2))/choose(18, 4)

# Egzaktno rjesenje
a_dio_sim
a_dio_egz

b_dio_sim
b_dio_egz


#Zadatak 3: 
#Novčić bacamo dok se dva puta za redom ne pojavi isti znak. Opišite vjerojatnosni prostor i izračunajte
#vjerojatnost da pokus završi u parnom broju bacanja.

#Rješenje, za pokus radimo jednu funkciju:
pokus = function() {
  bacanje = rbinom(1, size = 1, prob = 0.5)
  i = 1
  while (1) {
    sljedece_bacanje = rbinom(1, size = 1, prob = 0.5)
    i = i + 1
    if (sljedece_bacanje == bacanje) { #uvjet zaustavljanja pokusa
      break
    } else {
      bacanje = sljedece_bacanje
    }
  }
  return(i)
}
set.seed(1956819)
broj_ponavljanja = 10000
zavrsilo_u_parno_bacanja = 0
for (i in 1:broj_ponavljanja) zavrsilo_u_parno_bacanja = zavrsilo_u_parno_bacanja + ((pokus()%%2) == 0) #boolean TRUE se računa kao 1, FALSE je 0
rj_sim = zavrsilo_u_parno_bacanja/broj_ponavljanja
rj_egz = 2/3
rj_sim
rj_egz  


#Zadatak 4: U poslovnici A nalazi se 100 srećki od kojih je 25 dobitnih, a u poslovnici B 55 srećki od kojih je 5 dobitnih.
#Marko baca simetričnu kocku - ako na kocki padne broj 1 kupuje dvije srećke u poslovnici A, ako na kocki
#padne 2 kupuje dvije srećke u poslovnici B, inače kupuje po jednu srećku u svakoj poslovnici. Kolika je
#vjerojatnost da je točno jedna kupljena srećka dobitna?


# oznacimo s 0 listice koji nisu dobitni, a s 1 dobitne
# listice
set.seed(5382523)
A = rep(0:1, c(100 - 25, 25))
B = rep(0:1, c(55 - 5, 5))
broj_ponavljanja = 10000
tocno_jedna_dobitna = 0
for (i in 1:broj_ponavljanja) {
  kocka = sample.int(6, size = 1)
  if (kocka == 1) {   #računamo koliko ima pozitivnih događaja, ovisno o hipotezama
    uzorak = sample(A, size = 2, replace = FALSE)
  } else if (kocka == 2) {
    uzorak = sample(B, size = 2, replace = FALSE)
  } else {
    uzorak = c(sample(A, size = 1), sample(B, size = 1))
  }
  tocno_jedna_dobitna = tocno_jedna_dobitna + (sum(uzorak) ==
                                                 1)
}
rj_sim = tocno_jedna_dobitna/broj_ponavljanja
rj_egz = 1027/3564
rj_sim
rj_egz

#Zadatak 5: Konobar počinje smjenu s 0kn. Od svakog gosta dobije napojnicu i to od 10kn ili 5kn, pri čemu je manja
#napojnica dva puta vjerojatnija. Izračunajte vjerojatnost da je konobar dobio manje od 30kn od 4 gosta

#Rješenje
set.seed(105179)
broj_ponavljanja = 10000
broj_vecih_napojnica = rbinom(broj_ponavljanja, size = 4, prob = 1/3) #ponavljaj broj_ponavljanja puta uzorkovanje iz B(4, 1/3)
ukupna_napojnica = 10 * broj_vecih_napojnica + 5 * (4 - broj_vecih_napojnica)
rj_sim = sum(ukupna_napojnica < 30)/broj_ponavljanja
rj_egz = pbinom(1, size = 4, prob = 1/3) #prefix p daje P(X <= 1), odnosno funkciju distribucije
rj_sim
rj_egz


#Zadatak 6: Na letu ima mjesta za 300 putnika, pri čemu će putnik koji je kupio kartu zakasniti na njega s vjerojatnošću
#0.01. Stoga je aviokompanija prodala više karata, njih 302. Kolika ja vjerojatnost da će na letu biti mjesta
#za sve putnike s kartom?

#Rješenje:
set.seed(63962171)
broj_ponavljanja = 10000
broj_putnika_koji_kasne = rbinom(broj_ponavljanja, size = 302,
                                 prob = 0.01)
rj_sim = sum(broj_putnika_koji_kasne >= 2)/broj_ponavljanja
rj_egz = 1 - pbinom(1, size = 302, prob = 0.01)
rj_approx = 1 - ppois(1, lambda = 302 * 0.01)
rj_sim
## [1] 0.8037
7
rj_egz
## [1] 0.8053126
rj_approx
## [1] 0.8038191
# Ilustracija aproksimacije binomne slucajne varijable
# Poissonovom
n = 302
p = 0.01
barplot(rbind(dbinom(0:n, size = n, prob = p), dpois(0:n, lambda = n *
                                                      p)), beside = T, xlim = c(0, 22))
n = 302
p = 0.1
barplot(rbind(dbinom(0:n, size = n, prob = p), dpois(0:n, lambda = n *
                                                       p)), beside = T, xlim = c(0, 150))

#Zadatak 7: Na matematičkoj analizi ste naučili kako izračunati površine preko integrala, ali površina se 
#može računati i vjerojatnosno. Naime, lik čiju površinu želimo izračunati, nazovima ga A, može se uložiti u neki pravokutnik, nazovimo ga P.
#Ukoliko puno puta biramo točke uniformno iz tog pravokutnika, tada je P(izabrana točka je u A ) = površina(A) / površina(P), pa je
#površina(A) = P(izabrana točka je u A) * površina(P). 
#P(izabrana točka je u A) možemo izračunati tako da, kao i u prethodnim zadacima, puno puta ponavljamo pokus
#i izračunamo omjer točaka koje su u liku A. To je tzv. Monte Carlo metoda
#Monte Carlo metodom izračunajte površinu:
#a) kruga radijusa 1
#b) elipse x^2/2 + y^2/1 = 1
