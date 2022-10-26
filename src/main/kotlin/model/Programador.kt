package model

import java.util.*

data class Programador(
    val id: Int,
    val nombre: String,
    val fechaAlta: Date,
    val departamentoId: Int
) {
    override fun toString(): String {
        return "Programador(id=$id, nombre='$nombre', fechaAlta=$fechaAlta, departamentoId=$departamentoId)"
    }
}