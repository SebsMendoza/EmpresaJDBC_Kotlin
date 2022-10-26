package repository.departamento

import model.Departamento
import repository.CrudRepository

interface DepartamentoRepository : CrudRepository<Departamento, Int>