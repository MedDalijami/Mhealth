# ZdravMuc

Mobilna aplikacija za Android za podporo duševnemu počutju. Uporabnik beleži spanje, druženje, šport in splošno počutje, za vsak vnos pa skrbi za virtualno mačko, katere razpoloženje je odraz njegove skrbi zase.

Aplikacija je nastala v okviru diplomske naloge *Razvoj mobilne aplikacije za podporo duševnega počutja*.

## Testni uporabnik

Za prijavo v aplikacijo lahko uporabite testnega uporabnika:

| Polje  | Vrednost              |
| ------ | --------------------- |
| E-mail | `testni@uporabnik.si` |
| Geslo  | `testni123`           |

Testni uporabnik je **Jana Novak**. Lahko pa si s gumbom **»Registriraj se«** ustvarite tudi svoj račun.

> Podatki so shranjeni v oblaku (**Firebase**), zato ostanejo shranjeni tudi po ponovnem zagonu aplikacije in so na voljo na vseh napravah, na katerih se prijavite z istim računom. Vsak uporabnik vidi samo svoje podatke.

## Kako aplikacija deluje

### 1. Prijava (LogIn)

Aplikacija se začne na zaslonu za **prijavo**. Vnesite e-mail in geslo ter pritisnite **»Vpiši se«**.

- Gumb za prijavo postane aktiven šele, ko je e-mail veljavne oblike (vsebuje `@` in `.`) in ima geslo vsaj 8 znakov in vsaj eno številko.
- Pri napačnih podatkih ali brez internetne povezave se izpiše sporočilo o napaki.
- Aplikacija si prijavo zapomni: ob naslednjem zagonu se odpre neposredno domači zaslon.
- Z gumbom **»Registriraj se«** odprete zaslon za registracijo.

### 2. Registracija (SignUp)

Za novega uporabnika vnesete ime, priimek, e-mail, geslo in ponovitev gesla, po želji pa tudi profilno sliko. Po uspešni registraciji ste takoj prijavljeni in aplikacija odpre domači zaslon. Registracija z e-mailom, ki je že registriran, ni mogoča.

### 3. Domov (Home)

Po prijavi se odpre domači zaslon z animacijo mačke in **dnevnimi točkami**:

- vsak vnos prinese točke, šteje pa največ en vnos na kategorijo na dan:

  | Vnos                              | Točke  |
  | --------------------------------- | ------ |
  | šport, druženje, splošno počutje  | 10     |
  | spanje, krajše od 8 ur            | 10     |
  | spanje, 8 ur ali več              | **20** |

- največ je mogoče zbrati **50 točk na dan**; točke se vsak dan začnejo znova z 0,
- glede na današnje točke se spreminja razpoloženje mačke (od »Mucka se počuti okej« 😐 do »Mucka se počuti super« 😻) in njena animacija,
- vrstica napredka prikazuje današnje točke, pod njo pa so prikazane še vse zbrane točke.

Na dnu zaslona je vedno prikazana menijska vrstica (razen na prijavi in registraciji):

- **levi gumb** odpre meni funkcionalnosti,
- **desni gumb** odpre uporabniški meni.

### 4. Beleženje vnosov

V meniju funkcionalnosti so štiri kategorije. Vsaka ima svoj obrazec, ki ga odprete z gumbom na zaslonu (npr. »Zabeleži novo spanje«):

| Kategorija                | Kaj se beleži                                                                                      |
| ------------------------- | -------------------------------------------------------------------------------------------------- |
| 🌙 **Spanje**             | trajanje spanja (ure in minute), ocena z zvezdicami (1–5), komentar                                |
| 👾 **Druženje**           | način druženja, s kom (družina, prijatelji, sodelavci …), število ljudi, trajanje, ocena, komentar |
| 👟 **Šport in aktivnost** | tip aktivnosti, trajanje, ocena, komentar                                                          |
| 🫂 **Splošno počutje**    | ocena počutja, opis počutja, kaj je povzročalo skrb, kaj je bilo najbolj všeč                      |

Ko vnos uspešno shranite, se prikaže animacija potrditve v desnem kotu zaslona. Vnos se shrani tudi brez internetne povezave in se samodejno prenese v oblak, ko je povezava spet na voljo.

### 5. Pregled vnosov (Data)

V uporabniškem meniju izberete **»Pregled vnosov«**.

**Prikazani podatki** – izberete, katere vnose želite videti:

- **Vsi podatki** (privzeto) – vsi vnosi skupaj,
- **Spanje**, **Druženje**, **Šport in aktivnost** ali **Splošno počutje** – samo ena kategorija.

**Filtri** – z gumbom **»Filtri ▾«** jih odprete in zaprete. Ko je filter vklopljen, gumb prikazuje njegov povzetek (npr. »Filtri: Zadnjih 7 dni · 3 – 5 ⭐«):

- **Obdobje** – *Vsi vnosi*, *Danes*, *Zadnjih 7 dni*, *Zadnjih 30 dni* ali *Izberi obdobje …*, kjer v koledarju izberete poljuben začetni in končni dan,
- **Ocena** – z drsnikom izberete najnižjo in najvišjo oceno (npr. 3–5 ⭐), prikazani so samo vnosi z oceno v tem razponu.

**Način prikaza**:

- **Graf** – tortni graf vnosov:
  - pri eni kategoriji graf prikazuje, koliko vnosov ima posamezno oceno (1–5 ⭐),
  - pri *Vsi podatki* graf prikazuje, koliko vnosov je v posamezni kategoriji,
- **Seznam** – seznam vnosov, od najnovejšega naprej. Pri *Vsi podatki* ima vsak vnos oznako kategorije. Vnos izbrišete tako, da ga povlečete vstran.

### 6. Uporabnik (User)

V uporabniškem meniju izberete **»Uporabnik«**, kjer lahko:

- uredite profil (ime, priimek, profilna slika); e-mail je prikazan, vendar ga ni mogoče spremeniti,
- spremenite geslo (za potrditev je treba vnesti trenutno geslo),
- se odjavite (**»Izpis iz profila«**) – vrne vas na prijavo,
- izbrišete uporabnika (za potrditev je treba vnesti geslo). Ob izbrisu se izbrišejo uporabniški račun, profil, profilna slika in **vsi vnosi**.

### 7. Sporočila mucke

Mucka vsak dan okoli **18. ure** pošlje kratko spodbudno sporočilo, npr. »🐱 Mucka ti želi lep dan! – Ne pozabi na kratek sprehod in kozarec vode. 💧«.

- Sporočilo se izbere naključno med desetimi, dvakrat zapored se isto sporočilo ne ponovi.
- Sporočila se prikazujejo tudi, ko je aplikacija zaprta, in delujejo brez internetne povezave.
- Ob prvem odprtju domačega zaslona aplikacija vpraša za dovoljenje za obvestila.
- Po odjavi ali izbrisu uporabnika se sporočila ustavijo, po ponovni prijavi pa spet začnejo.
- Sporočila lahko izklopite v nastavitvah telefona (Aplikacije → ZdravMuc → Obvestila).

## Tehnologije

- **Kotlin** in **Jetpack Compose** (Material 3)
- arhitektura **MVVM** (ViewModel + StateFlow) z repozitoriji
- **Firebase**:
  - **Authentication** – registracija in prijava z e-mailom in geslom,
  - **Cloud Firestore** – shranjevanje profila in vnosov v oblaku, sinhronizacija v realnem času in delovanje brez povezave,
  - **Security Rules** – vsak uporabnik lahko bere in piše samo svoje podatke, pravila preverjajo tudi pravilnost podatkov (npr. ocena 1–5),
- profilna slika se pomanjša na 256 × 256 px in shrani kot del uporabnikovega profila v Firestore (brezplačni paket Spark, brez Cloud Storage)
- **WorkManager** – načrtovanje dnevnih sporočil mucke v ozadju (lokalna obvestila, brez strežnika)
- knjižnica **YCharts** za grafe
- **Media3 ExoPlayer** za animacije mačke
- minimalna različica Androida: API 35, ciljna: API 36

### Struktura podatkov v Firestore

```
users/{uid}                      ime, priimek, e-mail, datum registracije, profilna slika
users/{uid}/sleep/{id}           vnosi spanja
users/{uid}/sport/{id}           vnosi športa in aktivnosti
users/{uid}/social/{id}          vnosi druženja
users/{uid}/wellbeing/{id}       vnosi splošnega počutja
```

### Varnost podatkov

Podatki so zaščiteni s **Firestore Security Rules**, ki se izvajajo na strežnikih Firebase in jih ni mogoče obiti iz aplikacije. Celotna pravila so v datoteki [`firestore.rules`](firestore.rules).

- Dostop imajo samo prijavljeni uporabniki, vsak uporabnik pa lahko bere in piše **samo svoje** podatke (`users/{uid}`).
- Dovoljene so samo zbirke, ki jih aplikacija uporablja (`sleep`, `sport`, `social`, `wellbeing`), vse ostalo je zavrnjeno.
- Vsak vnos mora imeti natanko pravilna polja in veljavne vrednosti (npr. ocena 1–5, trajanje do 24 ur, besedilo do 1000 znakov).
- Vnose je mogoče ustvariti, prebrati in izbrisati, urejanje pa ni dovoljeno.
- E-pošte in datuma registracije v profilu ni mogoče spremeniti, profilna slika je omejena na približno 150 KB.
- Ob izbrisu uporabnika se izbrišejo vsi njegovi podatki.

## Zagon

1. Odprite mapo `MHealthCat` v **Android Studiu**.
2. Počakajte, da se Gradle sinhronizira.
3. Zaženite aplikacijo na emulatorju ali napravi z Androidom 15 (API 35) ali novejšim. Naprava potrebuje internetno povezavo za prijavo in registracijo. Ob prvem odprtju domačega zaslona dovolite obvestila, da boste prejemali sporočila mucke.
4. Prijavite se s testnim uporabnikom (`testni@uporabnik.si` / `testni123`) ali si ustvarite svoj račun.
5. Preizkusite aplikacijo: zabeležite nekaj vnosov, spremljajte točke na domačem zaslonu in si oglejte vnose v »Pregled vnosov«.
