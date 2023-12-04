package com.theo.composecalendar.models

sealed interface HighlightDate {
    object Current : HighlightDate
    object None : HighlightDate
}