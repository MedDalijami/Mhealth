package com.example.mhealthcat.data.repositories

import com.example.mhealthcat.data.forms.SleepForm
import com.example.mhealthcat.data.forms.SocialForm
import com.example.mhealthcat.data.forms.SportForm
import com.example.mhealthcat.data.forms.WellbeingForm


class SleepRepository: DataRepository<SleepForm> (EntryCollection.SLEEP, SleepForm::class.java){
    override fun stampMetadata(
        draft: SleepForm,
        id: String,
        timestamp: Long
    ): SleepForm = draft.copy(id = id, timestamp = timestamp)

}

class SportRepository: DataRepository<SportForm>(EntryCollection.SPORT, SportForm::class.java) {
    override fun stampMetadata(
        draft: SportForm,
        id: String,
        timestamp: Long
    ): SportForm = draft.copy(id = id, timestamp = timestamp)

}

class WellbeingRepository: DataRepository<WellbeingForm>(EntryCollection.WELLBEING, WellbeingForm::class.java) {

    override fun stampMetadata(
        draft: WellbeingForm,
        id: String,
        timestamp: Long
    ): WellbeingForm = draft.copy(id = id, timestamp = timestamp)

}

class SocialRepository: DataRepository<SocialForm>(EntryCollection.SOCIAL, SocialForm::class.java) {

    override fun stampMetadata(
        draft: SocialForm,
        id: String,
        timestamp: Long
    ): SocialForm = draft.copy(id = id, timestamp = timestamp)

}

object MhealthDataRepositories {
    val sleepRepository = SleepRepository()
    val sportRepository = SportRepository()
    val wellbeingRepository = WellbeingRepository()
    val socialRepository = SocialRepository()
}