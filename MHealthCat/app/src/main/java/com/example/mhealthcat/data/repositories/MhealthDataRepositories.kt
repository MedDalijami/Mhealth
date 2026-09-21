package com.example.mhealthcat.data.repositories

import com.example.mhealthcat.data.forms.SleepForm
import com.example.mhealthcat.data.forms.SocialForm
import com.example.mhealthcat.data.forms.SportForm
import com.example.mhealthcat.data.forms.WellbeingForm
import com.example.mhealthcat.testData.TestData


class SleepRepository(initialItems: List<SleepForm> = emptyList()) : DataRepository<SleepForm>(initialItems) {
    override fun stampMetadata(
        draft: SleepForm,
        id: String,
        timestamp: Long
    ): SleepForm = draft.copy(id = id, timestamp = timestamp)

}

class SportRepository(initialItems: List<SportForm> = emptyList()) : DataRepository<SportForm>(initialItems) {
    override fun stampMetadata(
        draft: SportForm,
        id: String,
        timestamp: Long
    ): SportForm = draft.copy(id = id, timestamp = timestamp)

}

class WellbeingRepository(initialItems: List<WellbeingForm> = emptyList()) : DataRepository<WellbeingForm>(initialItems) {

    override fun stampMetadata(
        draft: WellbeingForm,
        id: String,
        timestamp: Long
    ): WellbeingForm = draft.copy(id = id, timestamp = timestamp)

}

class SocialRepository(initialItems: List<SocialForm> = emptyList()) : DataRepository<SocialForm>(initialItems) {

    override fun stampMetadata(
        draft: SocialForm,
        id: String,
        timestamp: Long
    ): SocialForm = draft.copy(id = id, timestamp = timestamp)

}

object MhealthDataRepositories {
    val sleepRepository = SleepRepository(TestData.sleepData)
    val sportRepository = SportRepository(TestData.sportData)
    val wellbeingRepository = WellbeingRepository(TestData.wellbeingData)
    val socialRepository = SocialRepository(TestData.socialData)
}