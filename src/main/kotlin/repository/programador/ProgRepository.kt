package repository.programador

import database.DataBaseManager
import model.Departamento
import model.Programador
import mu.KotlinLogging
import java.util.*

private val log = KotlinLogging.logger { }

class ProgRepository : ProgramadorRepository {
    override fun findAll(): List<Programador> {
        val query = "SELECT * FROM programador"
        DataBaseManager.open()
        val result = DataBaseManager.select(query)
        val programadores = mutableListOf<Programador>()
        result?.let {
            while (result.next()) {
                val programador = Programador(
                    id = it.getInt("id"),
                    nombre = it.getString("nombre"),
                    fechaAlta = it.getObject("fecha") as Date,
                    departamentoId = it.getInt("id_dept")
                )
                programadores.add(programador)
            }
        }
        DataBaseManager.close()
        return programadores.toList()
    }

    override fun findById(id: Int): Programador? {
        val query = "SELECT * FROM programador WHERE id_dept = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var programador: Programador? = null
        result?.let {
            if (result.next()) {
                programador = Programador(
                    id = result.getInt("id"),
                    nombre = it.getString("nombre"),
                    fechaAlta = it.getObject("fecha") as Date,
                    departamentoId = it.getInt("id_dept")
                )
            }
        }
        DataBaseManager.close()
        log.debug("Programadores encontrados con $id dept: $programador")
        return programador
    }

    override fun save(entity: Programador): Programador {
        val programador = findById(entity.id)
        programador?.let {
            return update(entity)
        } ?: run {
            return insert(entity)
        }
    }

    private fun insert(programador: Programador): Programador {
        val query = """INSERT INTO programador
            |(id, nombre, fecha, id_dept)
            | VALUES (?, ?, ?, ?)
        """.trimMargin()
        DataBaseManager.open()
        val result = DataBaseManager.insert(
            query,
            programador.id,
            programador.nombre,
            programador.fechaAlta,
            programador.departamentoId
        )
        DataBaseManager.close()
        log.debug { "Programador insertado: $programador - Resultado: ${result == 1}" }
        return programador
    }

    private fun update(programador: Programador): Programador {
        val query = """UPDATE departamento
            |SET nombre = ?, fecha = ?, id_dept = ? WHERE id = ?
        """.trimMargin()
        DataBaseManager.open()
        val result =
            DataBaseManager.update(query, programador.nombre, programador.fechaAlta, programador.departamentoId)
        DataBaseManager.close()
        log.debug { "Programador actualizaco: $programador - Resultado: ${result == 1}" }
        return programador
    }

    override fun delete(entity: Programador): Boolean {
        val query = """DELETE FROM programador WHERE id = ?"""
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, entity.id)
        DataBaseManager.close()
        log.debug { "Programador eliminado: $entity - Resultado: ${result == 1}" }
        return result == 1
    }
}