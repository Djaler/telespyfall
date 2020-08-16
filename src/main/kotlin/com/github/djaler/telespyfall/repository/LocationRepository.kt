package com.github.djaler.telespyfall.repository

import com.github.djaler.telespyfall.entity.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<LocationEntity, Short>
