package com.example.mhealthcat.testData

import com.example.mhealthcat.data.forms.SleepForm
import com.example.mhealthcat.data.forms.SocialForm
import com.example.mhealthcat.data.forms.SportForm
import com.example.mhealthcat.data.forms.WellbeingForm
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

object TestData {


    val sleepData = rawSleepData.mapIndexed { index, item -> item.copy(id = UUID.randomUUID().toString()) }
    val socialData = rawSocialData.mapIndexed { index, item -> item.copy(id = UUID.randomUUID().toString()) }
    val sportData = rawSportData.mapIndexed { index, item -> item.copy(id = UUID.randomUUID().toString()) }
    val wellbeingData = rawWellbeingData.mapIndexed { index, item -> item.copy(id = UUID.randomUUID().toString()) }
}

private val rawSleepData = listOf(
    SleepForm(
        rating = 4,
        hours = 7,
        minutes = 30,
        comment = "Dobro sem spal, zjutraj sem se počutil spočitega.",
        timestamp = daysAgo(6)
    ),
    SleepForm(
        rating = 2,
        hours = 5,
        minutes = 0,
        comment = "Težko sem zaspal, veliko misli v glavi.",
        timestamp = daysAgo(5)
    ),
    SleepForm(
        rating = 5,
        hours = 8,
        minutes = 15,
        comment = "Odličen spanec, najboljši v zadnjem času!",
        timestamp = daysAgo(4)
    ),
    SleepForm(
        rating = 3,
        hours = 6,
        minutes = 45,
        comment = "Enkrat sem se zbudil sredi noči.",
        timestamp = daysAgo(3)
    ),
    SleepForm(
        rating = 1,
        hours = 3,
        minutes = 20,
        comment = "Zelo nemiren spanec, imel sem slabe sanje.",
        timestamp = daysAgo(2)
    ),
    SleepForm(
        rating = 4,
        hours = 7,
        minutes = 0,
        comment = "Soliden spanec, počutil sem se kar v redu.",
        timestamp = daysAgo(1)
    ),
    SleepForm(
        rating = 5,
        hours = 9,
        minutes = 0,
        comment = "Vikend, dolgo sem spal in se odlično počutil.",
        timestamp = daysAgo(0)
    ),
)

private val rawSocialData = listOf(
    SocialForm(
        socialInteraction = "Nedeljsko kosilo doma",
        people = "Družina",
        numberOfPeople = 5,
        comment = "Nedeljsko kosilo pri starših, toplo in domače.",
        rating = 5,
        hours = 2,
        minutes = 30,
        timestamp = daysAgo(0)
    ),
    SocialForm(
        socialInteraction = "Rojstnodnevna zabava",
        people = "Prijatelji",
        numberOfPeople = 10,
        comment = "Veliko ljudi, a malo utrudljivo.",
        rating = 3,
        hours = 4,
        minutes = 0,
        timestamp = daysAgo(2)
    ),
    SocialForm(
        socialInteraction = "Telefonski klic",
        people = "Sodelavci",
        numberOfPeople = 1,
        comment = "Kratek klic glede projekta.",
        rating = 3,
        hours = 0,
        minutes = 45,
        timestamp = daysAgo(4)
    ),
    SocialForm(
        socialInteraction = "Videoklic z družino",
        people = "Družina",
        numberOfPeople = 2,
        comment = "Veseli smo se videli.",
        rating = 4,
        hours = 1,
        minutes = 30,
        timestamp = daysAgo(5)
    ),
    SocialForm(
        socialInteraction = "Večerja v restavraciji",
        people = "Prijatelji",
        numberOfPeople = 4,
        comment = "Zelo prijetno vzdušje.",
        rating = 5,
        hours = 3,
        minutes = 0,
        timestamp = daysAgo(6)
    ),
)

private val rawSportData = listOf(
    SportForm(
        rating = 5,
        hours = 1,
        minutes = 0,
        activity = "Tek",
        comment = "5 km jutranji tek, počutil sem se odlično.",
        timestamp = daysAgo(6)
    ),
    SportForm(
        rating = 4,
        hours = 0,
        minutes = 45,
        activity = "Joga",
        comment = "Sprostitvena joga zvečer, telo se je zahvalilo.",
        timestamp = daysAgo(5)
    ),
    SportForm(
        rating = 3,
        hours = 1,
        minutes = 30,
        activity = "Kolesarjenje",
        comment = "Kolesaril sem do parka in nazaj, vreme je bilo lepo.",
        timestamp = daysAgo(4)
    ),
    SportForm(
        rating = 2,
        hours = 0,
        minutes = 30,
        activity = "Plavanje",
        comment = "Bazen je bil prenatrpan, težko sem se skoncentriral.",
        timestamp = daysAgo(3)
    ),
    SportForm(
        rating = 5,
        hours = 2,
        minutes = 0,
        activity = "Pohodništvo",
        comment = "Čudovita pot v hribih, priporočam vsem.",
        timestamp = daysAgo(2)
    ),
    SportForm(
        rating = 4,
        hours = 1,
        minutes = 15,
        activity = "Fitnes",
        comment = "Nov osebni rekord pri potiskanici, ponosen sem.",
        timestamp = daysAgo(1)
    ),
    SportForm(
        rating = 3,
        hours = 0,
        minutes = 20,
        activity = "Hoja",
        comment = "Kratka hoja med odmorom za kosilo, osvežilno.",
        timestamp = daysAgo(0)
    ),
)

private val rawWellbeingData = listOf(
    WellbeingForm(
        rating = 5,
        generalFeelings = "Poln energije in motivacije.",
        generalFears = "Danes nisem imel posebnih skrbi.",
        somethingGoodThatHappened = "Šef me je pohvalil pred celotno ekipo.",
        timestamp = daysAgo(5)
    ),
    WellbeingForm(
        rating = 3,
        generalFeelings = "Malo utrujen in raztresen.",
        generalFears = "Skrbi me rok za oddajo projekta.",
        somethingGoodThatHappened = "Prijeten kosilo s prijateljem.",
        timestamp = daysAgo(4)
    ),
    WellbeingForm(
        rating = 2,
        generalFeelings = "Nemiren in brez energije.",
        generalFears = "Skrbi me zdravje družinskega člana.",
        somethingGoodThatHappened = "Zvečer sem gledal smešen film in se nasmejal.",
        timestamp = daysAgo(3)
    ),
    WellbeingForm(
        rating = 4,
        generalFeelings = "Miren in zadovoljen.",
        generalFears = "Malo me skrbijo finance ob koncu meseca.",
        somethingGoodThatHappened = "Končal sem knjigo, ki sem jo bral že mesece.",
        timestamp = daysAgo(2)
    ),
    WellbeingForm(
        rating = 5,
        generalFeelings = "Srečen in hvaležen za vse.",
        generalFears = "Nimam posebnih strahov danes.",
        somethingGoodThatHappened = "Preživel sem kakovosten čas z družino.",
        timestamp = daysAgo(1)
    ),
    WellbeingForm(
        rating = 1,
        generalFeelings = "Preveč obremenjen in pod stresom.",
        generalFears = "Bojim se, da ne bom zmogel vsega.",
        somethingGoodThatHappened = "Zjutraj sem si privoščil dobro kavo.",
        timestamp = daysAgo(0)
    ),
)




private fun daysAgo(n: Long): Long =
    LocalDate.now()
        .minusDays(n)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()