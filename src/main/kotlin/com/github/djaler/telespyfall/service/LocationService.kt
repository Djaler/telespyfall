package com.github.djaler.telespyfall.service

import com.github.djaler.telespyfall.entity.LocationEntity
import com.github.djaler.telespyfall.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository
) {
    fun getRandomLocation(): LocationEntity {
        return locationRepository.findAll().random()
    }

    fun getAllLocations(): List<LocationEntity> {
        return locationRepository.findAll()
    }
}
