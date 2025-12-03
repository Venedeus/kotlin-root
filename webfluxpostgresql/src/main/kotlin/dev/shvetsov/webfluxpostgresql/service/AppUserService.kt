package dev.shvetsov.webfluxpostgresql.service

import dev.shvetsov.webfluxpostgresql.model.AppUser
import dev.shvetsov.webfluxpostgresql.model.AppUserRequest
import dev.shvetsov.webfluxpostgresql.model.BadRequestException
import dev.shvetsov.webfluxpostgresql.model.NotFoundException
import dev.shvetsov.webfluxpostgresql.repository.AppUserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class AppUserService(
    private val appUserRepository: AppUserRepository
) {
    fun findAll(): Flux<AppUser> = appUserRepository.findAll()

    fun findById(id: Long): Mono<AppUser> = appUserRepository
        .findById(id)
        .switchIfEmpty(Mono.error { NotFoundException("User with id $id was not found.") })

    fun createUser(appUserRequest: AppUserRequest): Mono<AppUser> =
        findByEmailOrError(appUserRequest)
            .switchIfEmpty {
                appUserRepository.save(
                    AppUser(
                        name = appUserRequest.name,
                        email = appUserRequest.email
                    )
                )
            }

    fun updateUser(id: Long, appUserRequest: AppUserRequest): Mono<AppUser> =
        findById(id).flatMap { foundUser ->
            findByEmailOrError(appUserRequest).switchIfEmpty(
                appUserRepository
                    .save(
                        AppUser(
                            id = id,
                            name = appUserRequest.name,
                            email = appUserRequest.email
                        )
                    )
            )
        }

    private fun findByEmailOrError(appUserRequest: AppUserRequest): Mono<AppUser> =
        appUserRepository.findByEmail(appUserRequest.email)
            .flatMap { Mono.error<AppUser>(BadRequestException("User with email ${appUserRequest.email} already exists.")) }
}