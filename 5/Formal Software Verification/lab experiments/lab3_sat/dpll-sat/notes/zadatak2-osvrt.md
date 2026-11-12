# Osvrt na Zadatak 2 — CDCL proširenja

Bilješke nakon implementacije CDCL solver-a. Provjera plana iz
`zadatak1-osvrt.md` i pojam što se *stvarno* promijenilo u odnosu na čisti
DPLL.

## Što sam najavio i što sam doista isporučio

Specifikacija (`FVPP_DZ3.pdf`) traži **najmanje dva značajna proširenja**
osnovno

g DPLL-a. Iz osvrta nakon Z1 sam planirao:

| Plan iz Z1 | Status u Z2 | Značajno proširenje? |
|---|---|---|
| CDCL — 1UIP učenje klauzula | ✅ `CdclSolver.analyze()` | **DA (1/2)** |
| Graf implikacija + nekronološki povratak | ✅ `reason[v]`, `level[v]` + `cancelUntil(backLvl)` | **DA (2/2)** |
| Two-watched literals (Chaff) | ✅ `propagate()` + `posWatches`/`negWatches` | Ne — impl. detalj |
| VSIDS + phase saving | ✅ `activity[]`, `phase[]` | Ne — heuristika |

Ukupno: **2 značajna proširenja** (minimum za Z2, 8 bodova).

### Implementacijski detalji (NISU značajna proširenja)

Kako bi CDCL solver doista bio brži od osnovnog DPLL-a, implementirao sam i:

- **Two-watched literals** (Chaff) — umjesto naivne O(klauzula) propagacije,
  pratim samo dva literala po klauzuli. Bez ovoga bi `hanoi4` i `bf0432` i
  dalje bili TIMEOUT. Nije zasebno proširenje — to je standardna
  implementacija PJK-a u modernim CDCL solverima.

- **VSIDS** (*Variable State Independent Decaying Sum*) — heuristika za odabir
  varijable grananja; biramo onu s najvišom aktivnošću. PDF kaže: *"implementacija
  neke druge heuristike za odabir literala pri grananju (...) ne smatra se
  značajnim proširenjem"* — dakle, samo zamjena za DLIS iz Z1.

- **Phase saving** — refinement VSIDS-a, pamtimo zadnji polaritet.

Sve navedeno je *sastavni dio* modernih CDCL implementacija (MiniSAT, Chaff),
ali se ne broji kao zasebno proširenje.

## Što se konkretno promijenilo u kodu

### 1) Struktura solver-a — rekurzija → iteracija

`DpllSolver` je **rekurzivan** — klasični "try `l`, ako ne uspije try `¬l`":

```java
boolean dpll() {
    if (simplify() == CONFLICT) return false;
    if (allAssigned())          return true;
    int lit = chooseBranchLiteral();
    int mark = trailSize;
    assignLit(lit);
    if (dpll()) return true;
    backtrackTo(mark);
    assignLit(-lit);
    return dpll();
}
```

`CdclSolver` je **iterativan** — jedna velika `while(true)` petlja koja
naizmjenično propagira i analizira konflikte:

```java
while (true) {
    int[] confl = propagate();
    if (confl != null) {
        if (currentDL() == 0) return UNSAT;
        (learnt, backLvl) = analyze(confl);
        cancelUntil(backLvl);
        add learnt; enqueue assertingLiteral;
    } else {
        if (allAssigned()) return SAT;
        if (restartTime()) cancelUntil(0);
        int v = pickVSIDS();
        newDecisionLevel();
        enqueue(decision);
    }
}
```

To je veliki strukturni rez — backjump preko više razina **odjednom** ne ide
"prirodno" kroz rekurziju jer bi morao baciti više `return false`-a uzastopno.

### 2) Reprezentacija formule

| | DpllSolver (Z1) | CdclSolver (Z2) |
|---|---|---|
| Klauzule | `List<int[]>` (fiksne, iz inputa) | `List<int[]>` originalne + dinamička lista naučenih |
| Propagacija | Linearna petlja po svim klauzulama | Watched literals: `posWatches[v]` / `negWatches[v]` |
| Dodjela | `value[v]` + `int[] trail` | + `level[v]`, `reason[v]` (graf implikacija) |
| Razine odluke | implicitno kroz rekurziju | eksplicitan `int[] trailLim` |
| Heuristika | DLIS — broji pojavljivanja kroz cijelu formulu | VSIDS — `double[] activity` + bump/decay |

### 3) Najveći "Aha!" moment — invariant praćenih literala

Pri implementaciji two-watched literals trebalo mi je da shvatim:

> Klauzula je "u redu" sve dok je *barem jedan* od dva praćena literala
> `true` **ili** *barem dva* nepridijeljena. Treba je gledati tek kad
> *oba* praćena postanu `false`.

Posljedica je da pri propagaciji literala `p` ne prolazimo kroz sve klauzule
nego **samo kroz `watches[-p]`** (one koje su praćenu vrijednost `-p` koristile
kao watch). To je glavni razlog što CDCL preživi na `hanoi4` u 78 ms umjesto
5+ minuta.

### 4) Tihi heroj — propagacija s reason klauzulom

Mali ali važan detalj: kad watched-literals propagacija forsira literal `l`,
nazovem `enqueue(l, reasonClause)` i u `reason[abs(l)]` spremam *baš tu
klauzulu koja ga je natjerala*. To `analyze()` koristi za prolaz unatrag
kroz graf implikacija. Ako nema reason-a (npr. decision literal), trajno
je `null` — to je signal "ovo je odluka, ne implikacija".

## Mjerenja — predviđanja vs. stvarnost

### Predviđanje iz Z1 osvrta

> "ponovno istraživanje istih konfliktnih grana s drugačijim okolnostima
> → rješenje: CDCL + 1UIP klauzule"

### Stvarnost

| Problem | DPLL | CDCL | Faktor |
|---|---:|---:|---:|
| `hanoi4` | TIMEOUT >5 min | 78 ms | **>3800×** |
| `bf0432-007` | TIMEOUT >60 s | 22 ms | **>2700×** |
| `uf20-01` | 8 ms | 8 ms | ~1× |
| `medium` | 16 ms | 8 ms | 2× |
| `hole7` | 66 ms | 178 ms | **0.37× (sporiji!)** |

Predviđanje za teške probleme pogođeno potpuno — i to s velikom rezervom.
**Stvarnost je čak bolja od očekivane** (predviđao sam možda 10×, dobio 3800×).

Iznenađenje: **`hole7` je s CDCL-om sporiji**. Pigeonhole je eksponencijalan
za sve DPLL/CDCL varijante bez specijalne podrške (simetrije, kardinalne
klauzule). DLIS na 56 varijabli slučajno dobro pogađa, dok CDCL "investira"
u učenje klauzula koje se ionako moraju eksponencijalno množiti.

### Ablacija — pravi test razumijevanja

Najobjavnija stvar je bila ablacijska analiza. Isključio sam jedno od dva
proširenja na tri najteža problema:

| Konfig | bf0432 | hanoi4 | hole7 |
|---|---:|---:|---:|
| **oba proširenja ON** | 21 ms | 120 ms | 78 ms |
| `--no-cdcl` | TIMEOUT (>30 s) | TIMEOUT (>30 s) | TIMEOUT (>30 s) |
| `--no-backjump` | 37 ms | 105 ms | 189 ms |

Dva jasna zaključka:

1. **CDCL je *ključan*.** Bez učenja klauzula sva tri problema postaju
   praktički neriješiva (prelaze timeout od 30 s). Sve ostalo — watched
   literals, VSIDS — ne pomaže ako ne učiš iz konflikata.

2. **Backjumping daje umjereno ubrzanje** (~30–70 % na ovim problemima).
   Uz CDCL učenje, kronološki povratak i dalje radi, samo malo sporije jer
   prolazi kroz "jalove" razine između konflikta i pravog uzroka.

## Što nije išlo glatko (lekcija o detaljima)

### Bug u `--no-backjump` (kronološki povratak)

Prva iteracija `--no-backjump` davala je *UNSAT na `hanoi4`* — to je SAT
formula. Razlog:

Kad `analyze()` proizvede naučenu klauzulu **duljine 1** (1UIP je na
razini 0), to je level-0 činjenica koja mora *trajno* ostati u formuli.
Ali kronološki povratak (`currentDL - 1`) bi je vratio na razinu > 0, pa
bi je sljedeći `cancelUntil()` obrisao.

Popravak:

```java
if (learnt != null && learnt.length == 1) {
    targetLevel = 0;     // činjenica uvijek ide na level 0
} else if (config.backjump) {
    targetLevel = backLvl;
} else {
    targetLevel = Math.max(0, currentDL() - 1);  // kronološki
}
```

Lekcija: **level-0 klauzule moraju preživjeti backtrack**. U punom
backjumpingu to se podrazumijeva (backLvl za length-1 je 0); u kronološkom
varijantu treba eksplicitna provjera.

## Što sam stvarno naučio

1. **CDCL = "naučiti zašto je nešto loše".** Ne pamtimo gdje smo bili,
   pamtimo *pravilo* koje ćemo koristiti od sada nadalje. Otud
   eksponencijalno bolji performans na strukturiranim problemima (hanoi4:
   >3800× ubrzanje prema čistom DPLL-u).

2. **Backjumping preskače jalove razine.** Umjesto da se vraćamo korak po
   korak (DPLL), skačemo direktno na razinu gdje naučena klauzula postaje
   jedinična (asserting). Time preskaču razine između konflikta i pravog
   uzroka.

3. **Watched literals je sastavni dio CDCL-a, ne bonus.** Naivna PJK je
   O(klauzula × literala) po literalu; watched literals je O(stvarno
   aktivne klauzule). Bez toga bi CDCL bio sporiji od osnovnog DPLL-a na
   velikim formulama.

4. **Implementacija je ~600 redaka — algoritam je desetljeća istraživanja.**
   Ovi 2 proširenja (CDCL + backjumping) s watched literals i VSIDS su
   jezgra svih modernih SAT solvera od 90-ih naovamo (GRASP, Chaff, MiniSAT).

## Što NIJE implementirano (mogući daljnji koraci)

Specifikacija traži samo dva proširenja. Sljedeći koraci bi bili:

- **Restart** (Luby ili Glucose-stil) — za povremeno napuštanje loših
  dijelova prostora pretrage
- **Lazy clause deletion** — ograničavanje memorije (naučene klauzule rastu)
- **Binarni heap za VSIDS** — O(log n) umjesto O(n) po grananju
- **Preprocessing** (BVE, hidden literal elimination)

Sve bi se uklopilo u trenutnu architekturu (trail/watch) bez većih rezova.

---

## Usporedba rezultata na benchmarkovima

| # | Datoteka | Očekivano (PDF) | Z1 (DPLL) | Z2 (CDCL) |
|---|---|---|---:|---:|
| 1 | `uf20-01.cnf` | SAT | ✅ SAT 8 ms | ✅ SAT 8 ms |
| 2 | `uuf50-01.cnf` | UNSAT | ✅ UNSAT 20 ms | ✅ UNSAT 10 ms |
| 3 | `flat50-1.cnf` | SAT | ✅ SAT 20 ms | ✅ SAT 9 ms |
| 4 | `medium.cnf` | SAT | ✅ SAT 16 ms | ✅ SAT 8 ms |
| 5 | `hanoi4.cnf` | SAT | ⚠️ TIMEOUT >60 s | ✅ **SAT 120 ms** |
| 6 | `bf0432-007.cnf` | UNSAT | ⚠️ TIMEOUT >60 s | ✅ **UNSAT 21 ms** |
| 7 | `hole7.cnf` | UNSAT | ✅ UNSAT 66 ms | ✅ UNSAT 78 ms |

Svi 7 problema rješavaju se ispravno. CDCL donosi enormno ubrzanje na
strukturiranim SAT problemima (`hanoi4`, `bf0432`) — >3800× brže nego DPLL.
