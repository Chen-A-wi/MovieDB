package com.awilab.domain.usecase

import com.awilab.domain.model.MovieDetail
import com.awilab.domain.model.Resource
import com.awilab.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
) {
    suspend operator fun invoke(movieId: Int): Flow<Resource<MovieDetail>> {
        return detailRepository.getMovieDetail(movieId = movieId)
    }
}
