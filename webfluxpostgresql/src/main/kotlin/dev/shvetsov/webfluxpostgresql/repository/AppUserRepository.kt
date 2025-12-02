package dev.shvetsov.webfluxpostgresql.repository

import dev.shvetsov.webfluxpostgresql.model.AppUser
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface AppUserRepository: ReactiveCrudRepository<AppUser, Long> {
}