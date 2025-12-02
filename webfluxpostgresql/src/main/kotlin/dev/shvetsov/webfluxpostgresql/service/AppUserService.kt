package dev.shvetsov.webfluxpostgresql.service

import dev.shvetsov.webfluxpostgresql.model.AppUser
import dev.shvetsov.webfluxpostgresql.model.NotFoundException
import dev.shvetsov.webfluxpostgresql.repository.AppUserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AppUserService(
    private val appUserRepository: AppUserRepository
) {
    fun findAll(): Flux<AppUser> = appUserRepository.findAll()

    fun findById(id: Long): Mono<AppUser> = appUserRepository
        .findById(id)
        .switchIfEmpty(Mono.error { NotFoundException("User with id $id was not found.") })
}