package repository

interface CrudRepository<T, ID> {
    fun findAll(): List<T>
    fun findById(id: Int): T?
    fun save(entity: T): T
    fun delete(entity: T): Boolean
}