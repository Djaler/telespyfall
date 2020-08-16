package com.github.djaler.telespyfall.entity

import javax.persistence.*

@Entity
@Table(name = "locations")
data class LocationEntity(
    @Column
    val name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Short = 0
)
