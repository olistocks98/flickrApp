package com.example.flickrapp.helpers

fun String.toTagList(): List<String> {
    val hashtagRegex = Regex("#\\w+")
    val matches = hashtagRegex.findAll(this)
    return matches.map { it.value.replace("#", "") }.toList()
}

fun String.removeTags(): String {
    val hashtagRegex = Regex("#\\w+")
    return this
        .replace(hashtagRegex, "")
        .trim()
        .replace(Regex("\\s+"), " ")

}

fun List<String>.toCommaSeperatedString() : String{
    return this.joinToString(separator = ",")
}
