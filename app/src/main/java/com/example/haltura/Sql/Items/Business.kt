package com.example.haltura.Sql.Items

import android.media.Image

class Business {
    private lateinit var name: String
    private lateinit var about: String
    private lateinit var image: Image
    private lateinit var listOfWork : List<Work>

    constructor(name: String, about: String, image: Image, listOfWork: List<Work>) {
        this.name = name
        this.about = about
        this.image = image
        this.listOfWork = listOfWork
    }

    constructor(name: String, about: String, image: Image) {
        this.name = name
        this.about = about
        this.image = image
        this.listOfWork = emptyList()
    }
}