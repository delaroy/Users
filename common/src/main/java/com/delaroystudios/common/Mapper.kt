package com.delaroystudios.common

interface Mapper<Cache, Data> {

    fun from(cache: Cache): Data

    fun to(data: Data): Cache

    fun mapModelList(models: List<Cache>): List<Data> {
        return models.mapTo(mutableListOf(), ::from)
    }

    fun mapEntityList(entityList: List<Data>): List<Cache> {
        return entityList.mapTo(mutableListOf(), this::to)
    }
}