package com.example.mhealthcat.data.messages

import java.time.LocalDateTime
import kotlin.random.Random
import java.time.Duration

data class CatMsg(
    val title: String,
    val message: String
)

object CatMessages {

    const val DAYS_BETWEEN_MSGS = 1L
    const val MASSAGE_HOUR = 18

    val listOfMessages = listOf(
        CatMsg("🐱 Mucka ti želi lep dan! 🐱", "Ne pozabi na kratek sprehod in kozarec vode. 💧"),
        CatMsg("🐱 Ponosna sem nate 🐱", "Hvala, ker skrbiš zase. – tvoja mucka"),
        CatMsg("🐱 Mucka razmišlja o tebi 🐱", "Si danes že poklical_a koga, ki ga imaš rad_a?"),
        CatMsg("🐱 Mucka že zeha 🐱", "Dober spanec je najboljša skrb zase. Lahko noč, ko bo čas!"),
        CatMsg("🐱 Mucka verjame vate 🐱", "Tudi majhen korak je korak naprej."),
        CatMsg("🐱 Mucka bi šla ven 🐱", "Pet minut gibanja na svežem zraku naredi čudeže."),
        CatMsg("🐱 Mucka ti pošilja objem 🐱", "V redu je, če danes ni popoln dan."),
        CatMsg("🐱 Mucka ti sporoča: 🐱", "Vzemi si minutko in globoko zadihaj. Vdih … izdih …"),
        CatMsg("😺 Mucka je vesela 🐱", "Lepo, da si tukaj. Kako se danes počutiš?"),
        CatMsg("🐱 Mucka predlaga odmor 🐱", "Privošči si nekaj, kar te razveseli – tudi to je skrb zase.")
    )

    fun pickIndex(lastIndex: Int, random: Random = Random.Default): Int {
        var index = random.nextInt(listOfMessages.size)
        while (index == lastIndex && listOfMessages.size > 1) index=random.nextInt(listOfMessages.size)
        return index
    }

    fun delayUntilNextMessage(hour: Int, now: LocalDateTime = LocalDateTime.now()): Duration {
        var next = now.toLocalDate().atTime(hour, 0)
        if (now >= next) next = next.plusDays(1)
        return Duration.between(now, next)
    }

}