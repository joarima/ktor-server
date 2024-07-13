package owlstilttherheads.com.infrastructure.post

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.json.Json
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.statement.StatementContext
import owlstilttherheads.com.domain.post.Post
import owlstilttherheads.com.infrastructure.Database
import owlstilttherheads.com.usecase.post.PostRepository
import java.sql.ResultSet
import java.time.OffsetDateTime
import java.util.*

private val objectMapper: ObjectMapper = ObjectMapper()

class PostRepositoryImpl(
    private val jdbi: Jdbi = Database.jdbi,

    ) : PostRepository {
    override fun findAll(): List<Post> {
        return jdbi.withHandle<List<Post>, Exception> { handle ->
            val query = """
                SELECT 
                    id, 
                    created_at, 
                    updated_at, 
                    content::varchar as content_j, 
                    is_open, 
                    "order" 
                FROM post
                ORDER BY "order" DESC
            """.trimIndent()

            try {
                val result = handle.createQuery(query)
                    .map { rs, ctx -> postRowMapper(rs, ctx) }
                    .list()

                // See: https://stackoverflow.com/questions/38579231/hikaricp-select-queries-execute-roll-back-due-to-dirty-commit-state-on-close
                handle.commit()
                return@withHandle result
            } catch (e: Exception) {
                handle.rollback()
                throw e
            }
        }
    }

    private fun postRowMapper(rs: ResultSet, ctx: StatementContext): Post {
        val id = rs.getString("id")
        val createdAt = rs.getObject("created_at", OffsetDateTime::class.java)
        val updatedAt = rs.getObject("updated_at", OffsetDateTime::class.java)
        val content = rs.getString("content_j")
        val isOpen = rs.getBoolean("is_open")
        val order = rs.getInt("order")

        return Post(
            id = UUID.fromString(id),
            createdAt = createdAt,
            updatedAt = updatedAt,
            content = Json.parseToJsonElement(content),
            isOpen = isOpen,
            order = order
        )
    }
}