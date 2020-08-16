package com.github.djaler.telespyfall.entity

import javax.persistence.*

@Entity
@Table(name = "games_players")
data class GamePlayerEntity(
    @Column
    val gameId: Int,

    @Column
    val telegramId: Int,

    @Column
    val username: String,

    @Column
    val spy: Boolean = false,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0
)
