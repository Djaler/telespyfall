package com.github.djaler.telespyfall.entity

import javax.persistence.*

@Entity
@Table(name = "games")
data class GameEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationId")
    val location: LocationEntity,

    @Column
    val messageId: Long,

    @Column
    @Enumerated(EnumType.STRING)
    val state: GameState,

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "gameId")
    val players: List<GamePlayerEntity> = listOf(),

    @Column
    val spyCount: Int = 1,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0
)

enum class GameState {
    CREATED,
    STARTED,
    FINISHED
}
