package com

sealed class FieldsState {
    object FieldsAreValid: FieldsState()
    object FieldsAreInvalid: FieldsState()
}