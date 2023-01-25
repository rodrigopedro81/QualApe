package com.authentication

import org.koin.dsl.module

val authenticationModule = module {
    single<Authenticator> { Authentication() }
}