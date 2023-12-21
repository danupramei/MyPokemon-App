package com.pokemon.shared

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

typealias Inflate2<T> = (LayoutInflater) -> T