package com.bcit.myminiandroidapp.data

class AssetRepository(private val assetDao: AssetDao) {
    fun insertEntity(asset: LocalAsset) {
        assetDao.add(asset)
    }

    fun getAll(): List<LocalAsset> {
        return assetDao.getAll()
    }

    fun deleteEntity(asset: LocalAsset) {
        assetDao.delete(asset)
    }
}