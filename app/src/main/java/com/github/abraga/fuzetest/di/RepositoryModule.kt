package com.github.abraga.fuzetest.di

import com.github.abraga.fuzetest.data.repository.MatchesRepository
import com.github.abraga.fuzetest.data.repository.MatchesRepositoryImpl
import com.github.abraga.fuzetest.data.repository.PlayersRepository
import com.github.abraga.fuzetest.data.repository.PlayersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMatchesRepository(
        matchesRepositoryImpl: MatchesRepositoryImpl
    ): MatchesRepository

    @Binds
    abstract fun bindPlayersRepository(
        playersRepositoryImpl: PlayersRepositoryImpl
    ): PlayersRepository
}