package com.flexcode.wedate.home.data.model

data class Person(
    val url: String? = null,
    val description: String = "",
    val name: String ,
    val age: String ,
    val location: String,
    val isOnline: Boolean = false,
)

val potentialMatches = mutableListOf(
    Person(
        url = "https://images.unsplash.com/photo-1623039497055-e79fcaebd4ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
        description = "Musician, Photographer...",
        name = "Natalie",
        age = "20",
        location = "Seattle",
        isOnline = false
    ),
    Person(
        url = "https://images.unsplash.com/photo-1508002366005-75a695ee2d17?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=368&q=80",
        description = "Musician, Photographer...",
        name = "Alice",
        age = "20",
        location = "Nairobi,Kenya",
        isOnline = true
    ),
    Person(
        url = "https://plus.unsplash.com/premium_photo-1663036708687-6592c34230d4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8YmVhdXRpZnVsJTIwYmxhY2slMjB3b21hbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60",
        description = "Musician, Photographer...",
        name = "Dominic",
        age = "32",
        location = "Mombasa,Kenya",
        isOnline = true
    ),
    Person(
        url = "https://images.unsplash.com/photo-1539996663806-177cb1190504?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8YmVhdXRpZnVsJTIwYmxhY2slMjB3b21hbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60",
        description = "Musician, Photographer...",
        name = "Camile",
        age = "32",
        location = "Arusha,Tz",
        isOnline = true
    ),
    Person(
        url = "https://images.unsplash.com/photo-1506795660198-e95c77602129?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
        description = "Musician, Photographer...",
        name = "Penolope",
        age = "21",
        location = "Washington,Dc",
        isOnline = false
    ),
    Person(
        url = "https://images.unsplash.com/photo-1545912453-3d32e20f72bf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
        description = "Musician, Photographer...",
        name = "Mitchelle",
        age = "32",
        location = "Lagos,Nigeria",
        isOnline = true
    ),
    Person(
        url = "https://images.unsplash.com/photo-1552699611-e2c208d5d9cf?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGxhZHl8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        description = "Musician, Photographer...",
        name = "Pamela",
        age = "25",
        location = "Geneva",
        isOnline = false
    ),
    Person(
        url = "https://images.unsplash.com/photo-1621784563286-84f7646ef221?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fGxhZHl8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        description = "Musician, Photographer...",
        name = "Mercy",
        age = "21",
        location = "Kyiv,Ukraine",
        isOnline = true
    ),
    Person(
        url = "https://images.unsplash.com/photo-1627156096030-861f04ff550e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fGxhZHl8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        description = "Musician, Photographer...",
        name = "Halima",
        age = "28",
        location = "Mogadishu,Somalia",
        isOnline = false
    ),
    Person(
        url = "https://images.unsplash.com/photo-1539698103494-a76dd0436fbc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fGxhZHl8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
        description = "Musician, Photographer...",
        name = "Stacy",
        age = "21",
        location = "Roysambu,Kenya",
        isOnline = true
    ),
    Person(
        url = "https://images.unsplash.com/photo-1668069574922-bca50880fd70?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
        description = "Musician, Photographer...",
        name = "Pamela",
        age = "18",
        location = "Mexico",
        isOnline = true
    ),
)