package repository.programador

import model.Programador
import repository.CrudRepository

interface ProgramadorRepository : CrudRepository<Programador, Int>