package owlstilttherheads.com.infrastructure

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

object Database {
    private val config = HoconApplicationConfig(ConfigFactory.load("application.conf"))

    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = config.property("db.jdbcUrl").getString()
        driverClassName = "org.postgresql.Driver"
        username = config.property("db.user").getString()
        password = config.property("db.password").getString()
        maximumPoolSize = 2
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    }

    private val dataSource = HikariDataSource(hikariConfig)

    val jdbi: Jdbi = Jdbi.create(dataSource)
        .installPlugin(KotlinPlugin())
        .installPlugin(KotlinSqlObjectPlugin())
}