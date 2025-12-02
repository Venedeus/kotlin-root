package dev.shvetsov.webfluxpostgresql.controller

import dev.shvetsov.webfluxpostgresql.model.AppUser
import dev.shvetsov.webfluxpostgresql.service.AppUserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class AppUserController(
    private val appUserService: AppUserService
) {
    @GetMapping("/users")
    fun getAll() : Flux<AppUser> = appUserService.findAll()

    @GetMapping("/users/{id}")
    fun getById(@PathVariable id: Long) : Mono<AppUser> = appUserService.findById(id)
}