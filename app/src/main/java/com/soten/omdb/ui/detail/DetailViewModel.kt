package com.soten.omdb.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soten.omdb.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState = _detailUiState.asStateFlow()

    private val imdbID = saveStateHandle[KEY_ID] ?: ""

    init {
        getMovieDetail()
    }

    fun getMovieDetail() {
        if (imdbID.isEmpty()) {
            _detailUiState.value = DetailUiState.Error
            return
        }

        viewModelScope.launch {
            getMovieDetailUseCase.invoke(imdbID)
                .catch {
                    _detailUiState.value = DetailUiState.Error
                }
                .collectLatest {
                    _detailUiState.value = DetailUiState.Success(it)
                }
        }
    }

    companion object {
        const val KEY_ID = "KEY_ID"
    }
}