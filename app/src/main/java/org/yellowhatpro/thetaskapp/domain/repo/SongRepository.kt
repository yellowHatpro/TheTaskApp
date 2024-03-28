package org.yellowhatpro.thetaskapp.domain.repo

import kotlinx.coroutines.flow.Flow
import org.yellowhatpro.thetaskapp.data.entities.Song
import org.yellowhatpro.thetaskapp.utils.Result

interface SongRepository {
    fun getAllSongs() : Flow<Result<List<Song>>>
}