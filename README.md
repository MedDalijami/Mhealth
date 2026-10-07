# ZdravMuc

## Testni uporabnik

Za prijavo v aplikacijo uporabite testnega uporabnika:

| Polje  | Vrednost              |
| ------ | --------------------- |
| E-mail | `testni@uporabnik.si` |
| Geslo  | `testni123`           |

Testni uporabnik je **Jana Novak**. Ob zagonu ima aplikacija že naložene testne vnose za zadnjih 7 dni, da so seznami in grafi takoj napolnjeni.

> Opomba: prijava in registracija zaenkrat delujeta brez strežnika. Podatki so shranjeni samo v pomnilniku, zato se po ponovnem zagonu aplikacije vse spremembe izgubijo.

## Kako aplikacija deluje

### 1. Prijava (LogIn)

Aplikacija se začne na zaslonu za **prijavo**. Vnesite e-mail in geslo testnega uporabnika ter pritisnite **»Vpiši se«**.

- Gumb za prijavo postane aktiven šele, ko je e-mail veljavne oblike (vsebuje `@` in `.`) in ima geslo vsaj 8 znakov in vsaj eno številko.
- Pri napačnih podatkih se izpiše sporočilo o napaki.
- Z gumbom **»Registriraj se«** odprete zaslon za registracijo.

### 2. Registracija (SignUp)

Za novega uporabnika vnesete ime, priimek, e-mail, geslo in ponovitev gesla, po želji pa tudi profilno sliko. Po uspešni registraciji vas aplikacija vrne na prijavo. Registracija z e-mailom testnega uporabnika ni mogoča, ker ta uporabnik že obstaja.

### 3. Domov (Home)

Po prijavi se odpre domači zaslon z animacijo mačke. Na dnu zaslona je vedno prikazana menijska vrstica (razen na prijavi in registraciji):

- **levi gumb ** odpre meni funkcionalnosti,
- **desni gumb** odpre uporabniški meni.

### 4. Beleženje vnosov

V meniju funkcionalnosti so štiri kategorije. Vsaka ima svoj obrazec, ki ga odprete z gumbom na zaslonu (npr. »Zabeleži novo spanje«):

| Kategorija                | Kaj se beleži                                                                                      |
| ------------------------- | -------------------------------------------------------------------------------------------------- |
| 🌙 **Spanje**            | trajanje spanja (ure in minute), ocena z zvezdicami (1–5), komentar                                |
| 👾 **Druženje**          | način druženja, s kom (družina, prijatelji, sodelavci …), število ljudi, trajanje, ocena, komentar |
| 👟 **Šport in aktivnost** | tip aktivnosti, trajanje, ocena, komentar                                                          |
| 🫂 **Splošno počutje**   | ocena počutja, opis počutja, kaj je povzročalo skrb, kaj je bilo najbolj všeč                      |

Ko vnos uspešno shranite, se prikaže animacija potrditve v desnem kotu zaslona.

### 5. Pregled vnosov (Data)

V uporabniškem meniju izberete **»Pregled vnosov«**. Izberete kategorijo podatkov in način prikaza:

- **Grafični prikaz** – tortni graf vnosov,
- **Prikazano kot seznam** – seznam vseh vnosov, kjer lahko posamezen vnos tudi izbrišete.

### 6. Uporabnik (User)

V uporabniškem meniju izberete **»Uporabnik«**, kjer lahko:

- uredite profil (ime, priimek, e-mail, profilna slika),
- spremenite geslo,
- se odjavite (**»Izpis iz profila«**) – vrne vas na prijavo,
- izbrišete uporabnika (za potrditev je treba vnesti geslo).

## Tehnologije

- **Kotlin** in **Jetpack Compose** (Material 3)
- arhitektura **MVVM** (ViewModel + StateFlow)
- knjižnica **YCharts** za grafe
- minimalna različica Androida: API 35, ciljna: API 36

## Zagon

1. Odprite mapo `MHealthCat` v **Android Studiu**.
2. Počakajte, da se Gradle sinhronizira.
3. Zaženite aplikacijo na emulatorju ali napravi z Androidom 15 (API 35) ali novejšim.
4. Prijavite se s testnim uporabnikom (`testni@uporabnik.si` / `testni12`
5. Preizkusite aplikacij