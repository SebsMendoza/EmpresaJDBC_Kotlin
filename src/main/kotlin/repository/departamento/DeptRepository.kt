package repository.departamento

import database.DataBaseManager
import model.Departamento
import model.Programador
import mu.KotlinLogging
import org.h2.command.dml.Update
import java.util.Date

private val log = KotlinLogging.logger { }

class DeptRepository : DepartamentoRepository {
    override fun findAll(): List<Departamento> {
        val query = "SELECT * FROM departamento"
        DataBaseManager.open()
        val result = DataBaseManager.select(query)
        val departamentos = mutableListOf<Departamento>()
        result?.let {
            while (result.next()) {
                val departamento = Departamento(
                    id = it.getInt("id"),
                    nombre = it.getString("nombre"),
                    presupuesto = it.getDouble("presupuesto")
                )
                departamentos.add(departamento)
            }
        }
        DataBaseManager.close()
        return departamentos.toList()
    }

    override fun findById(id: Int): Departamento? {
        val query = "SELECT * FROM departamento WHERE id = ?"
        DataBaseManager.open()
        val result = DataBaseManager.select(query, id)
        var departamento: Departamento? = null
        result?.let {
            if (result.next()) {
                departamento = Departamento(
                    id = it.getInt("id"),
                    nombre = it.getString("nombre"),
                    presupuesto = it.getDouble("presupuesto")
                )
            }
        }
        DataBaseManager.close()
        log.debug { "Departamento encontrado con $id: $departamento" }
        return departamento
    }

    override fun save(entity: Departamento): Departamento {
        val departamento = findById(entity.id)
        departamento?.let {
            return update(entity)
        } ?: run {
            return insert(entity)
        }
    }

    private fun insert(departamento: Departamento): Departamento {
        val query = """INSERT INTO departamento
            |(id, nombre, presupuesto)
            | VALUES (?, ?, ?)
        """.trimMargin()
        DataBaseManager.open()
        val result = DataBaseManager.insert(
            query,
            departamento.id,
            departamento.nombre,
            departamento.presupuesto
        )
        DataBaseManager.close()
        log.debug { "Departamento insertado: $departamento - Resultado: ${result == 1}" }
        return departamento
    }

    private fun update(departamento: Departamento): Departamento {
        val query = """UPDATE departamento SET nombre = ?, presupuesto = ? WHERE id = ?"""
            .trimMargin()
        DataBaseManager.open()
        val result = DataBaseManager.update(query, departamento.id, departamento.nombre, departamento.presupuesto)
        DataBaseManager.close()
        log.debug { "Departamento actualizado: $departamento - Resultado: ${result == 1}" }
        return departamento
    }

    override fun delete(entity: Departamento): Boolean {
        val query = """DELETE FROM departamento WHERE id = ?"""
        DataBaseManager.open()
        val result = DataBaseManager.delete(query, entity.id)
        DataBaseManager.close()
        log.debug { "Departamento eliminado: $entity - Resultado: ${result == 1}" }
        return result == 1
    }
}