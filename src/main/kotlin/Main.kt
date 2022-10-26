import controller.DepartamentoController
import controller.ProgramadorController
import database.DataBaseManager
import database.getNewDpt
import mu.KotlinLogging
import repository.departamento.DeptRepository
import repository.programador.ProgRepository

private val log = KotlinLogging.logger { }
fun main() {

    initDataBase()

    //Iniciación controllers
    val dptController = DepartamentoController(DeptRepository())
    val prgmController = ProgramadorController(ProgRepository())

    //CRUD Departamento
    println("Insertando nuevos departamentos:")
    getNewDpt().forEach { dpt ->
        dptController.createDepartamento(dpt)
    }
    println("Listar todos los departamentos: ")
    var departamentos = dptController.getDepartamentos()
    departamentos.forEach { departamento ->
        println(departamento)
    }

    println("Obtener un departamento por su id")
    val departamento = dptController.getDeptByID(departamentos[3].id)
    departamento?.let { println(it) }

    println("Modificar un parámetro del departamento según su id")
    departamento?.let {
        it.presupuesto += 500000.0
        dptController.updateDepartamento(it)
    }
    dptController.getDeptByID(departamentos[3].id)?.let { println(it) }

    /*println("Eliminar un departamento")
    val seccion = dptController.getDeptByID(departamentos[1].id)
    seccion?.let { if (dptController.deleteDepartamento(it)) println("Departamento eliminado") }
    */

}

fun initDataBase() {
    DataBaseManager.open()
    DataBaseManager.initData("sql/datos.sql")
    DataBaseManager.close()
}