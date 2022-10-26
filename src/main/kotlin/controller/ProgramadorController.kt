package controller

import model.Programador
import repository.programador.ProgramadorRepository

class ProgramadorController(private val programadorRepository: ProgramadorRepository) {
    fun getProgramadores(): List<Programador> {
        return programadorRepository.findAll()
    }

    fun getProgById(id: Int): Programador? {
        return programadorRepository.findById(id)
    }

    fun createProgramador(programador: Programador): Programador {
        programadorRepository.save(programador)
        return programador
    }

    fun updateProgramador(programador: Programador) {
        programadorRepository.save(programador)
    }

    fun deleteProgramador(programador: Programador): Boolean {
        return programadorRepository.delete(programador)
    }
}