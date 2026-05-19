package com.example.mhealthcat.forms

data class SocialForm(
    val socialInteraction: String = "Osebno",
    val socialInteractionOther: String = "",
    val people: String = "Prijatelji",
    val numberOfPeople: Int = 1,
    val comment: String = "",
    val rating: Int = 1,
    val hours: Int = 0,
    val minutes: Int = 0

) {
    val isOther get() = socialInteraction == "Drugo"

    fun increaseNumberOfPeople() = copy(numberOfPeople = numberOfPeople + 1)
    fun decreaseNumberOfPeople() = copy(numberOfPeople = numberOfPeople - 1)


}