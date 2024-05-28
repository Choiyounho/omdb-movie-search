package com.soten.omdb.ui.detail

import com.soten.omdb.domain.model.detail.MovieDetailModel

sealed interface DetailUiState {

    object Loading : DetailUiState

    data class Success(
        val movieDetailModel: MovieDetailModel
    ) : DetailUiState

    object Error : DetailUiState
}