package org.yellowhatpro.thetaskapp.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.yellowhatpro.thetaskapp.data.api.ApiService
import org.yellowhatpro.thetaskapp.data.entities.Song
import org.yellowhatpro.thetaskapp.domain.repo.SongRepository
import org.yellowhatpro.thetaskapp.utils.Result
import org.yellowhatpro.thetaskapp.utils.collectResult
import javax.inject.Inject

class SongRemoteRepository @Inject constructor(private val apiService: ApiService) : SongRepository {
    override fun getAllSongs(): Flow<Result<List<Song>>> = flow {
        apiService.getAllSongs().collectResult(
            onFailure = {
                emit(Result.failed(it))
            },
            onSuccess = {
                emit(Result.success(it))
            }
        )
    }
}