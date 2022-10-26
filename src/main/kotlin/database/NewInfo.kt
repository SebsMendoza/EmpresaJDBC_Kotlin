package database

import model.Departamento

fun getNewDpt() = listOf(
    Departamento(
        id = 4,
        nombre = "Diseño",
        presupuesto = 2000.0
    ),
    Departamento(
        id = 5,
        nombre = "Sistemas",
        presupuesto = 40000.0
    )
)