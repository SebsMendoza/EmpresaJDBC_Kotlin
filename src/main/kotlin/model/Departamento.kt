package model

data class Departamento(
    val id: Int,
    var nombre: String,
    var presupuesto: Double
) {
    override fun toString(): String {
        return "Departamento(id=$id, nombre='$nombre', presupuesto=$presupuesto)"
    }
}