package database

import mu.KotlinLogging
import org.apache.ibatis.jdbc.ScriptRunner
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.Reader
import java.sql.*


private val log = KotlinLogging.logger { }

object DataBaseManager {
    private var jdbcDriver: String = "org.h2.Driver"
    private var connection: Connection? = null
    private var preparedStatement: PreparedStatement? = null
    private lateinit var serverUrl: String
    private lateinit var serverPort: String
    private lateinit var dataBaseName: String
    private lateinit var sql: String

    init {
        initConfig()
    }

    private fun initConfig() {
        serverUrl = "localhost"
        serverPort = "3306"
        dataBaseName = "empresa"
        jdbcDriver = "org.h2.Driver"
        sql = "sql/datos.sql"
    }

    @Throws(SQLException::class)
    fun open() {
        var url =
            "jdbc:h2:mem:${this.dataBaseName};DB_CLOSE_DELAY=-1;"
        connection = DriverManager.getConnection(url)
        log.debug { "Conexión establecida: $url" }
    }

    @Throws(SQLException::class)
    fun close() {
        preparedStatement?.close()
        connection?.close()
        log.debug { "Conexión cerrada" }
    }

    @Throws(SQLException::class)
    private fun executeQuery(querySQL: String, vararg params: Any?): ResultSet? {
        preparedStatement = connection?.prepareStatement(querySQL)
        return preparedStatement?.let {
            for (i in params.indices) {
                it.setObject(i + 1, params[i])
            }
            log.debug { "Ejecutando consulta: $querySQL con parámetros: ${params.contentToString()}" }
            it.executeQuery()
        }
    }

    @Throws(SQLException::class)
    fun select(querySQL: String, vararg params: Any?): ResultSet? {
        return executeQuery(querySQL, *params)
    }

    @Throws(SQLException::class)
    fun select(querySQL: String, limit: Int, offset: Int, vararg params: Any?): ResultSet? {
        val query = "$querySQL LIMIT $limit OFFSET $offset"
        return executeQuery(query, *params)
    }

    @Throws(SQLException::class)
    fun insert(insertSQL: String, vararg params: Any?): Int {
        return updateQuery(insertSQL, *params)
    }

    @Throws(SQLException::class)
    fun update(updateSQL: String, vararg params: Any?): Int {
        return updateQuery(updateSQL, *params)
    }

    @Throws(SQLException::class)
    fun delete(deleteSQL: String, vararg params: Any?): Int {
        return updateQuery(deleteSQL, *params)
    }

    @Throws(SQLException::class)
    private fun updateQuery(genericSQL: String, vararg params: Any?): Int {
        // Con return generated keys obtenemos las claves generadas
        preparedStatement = connection?.prepareStatement(genericSQL)
        // Si hay parámetros, los asignamos
        return preparedStatement?.let {
            for (i in params.indices) {
                preparedStatement!!.setObject(i + 1, params[i])
            }
            log.debug { "Ejecutando consulta: $genericSQL con parámetros: ${params.contentToString()}" }
            it.executeUpdate()
        } ?: 0
    }

    @Throws(FileNotFoundException::class)
    fun initData(sqlFile: String?) {
        val sr = ScriptRunner(connection)
        sr.setEscapeProcessing(false)
        val reader: Reader = BufferedReader(FileReader(sqlFile))
        sr.runScript(reader)
    }
}