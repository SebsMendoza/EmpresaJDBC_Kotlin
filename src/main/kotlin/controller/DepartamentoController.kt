package controller

import model.Departamento
import mu.KotlinLogging
import repository.departamento.DepartamentoRepository

private val log = KotlinLogging.logger { }

class DepartamentoController(private val departamentoRepository: DepartamentoRepository) {
    fun getDepartamentos(): List<Departamento> {
        return departamentoRepository.findAll()
    }

    fun getDeptByID(id: Int): Departamento? {
        return departamentoRepository.findById(id)
    }

    fun createDepartamento(departamento: Departamento): Departamento {
        departamentoRepository.save(departamento)
        return departamento
    }

    fun updateDepartamento(it: Departamento) {
        departamentoRepository.save(it)
    }

    fun deleteDepartamento(departamento: Departamento): Boolean {
        return departamentoRepository.delete(departamento)
    }
}