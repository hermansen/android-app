package se.swosch.jackson.databinding

interface BindableListAdapter<T> {

    fun setItems(items: T)
}