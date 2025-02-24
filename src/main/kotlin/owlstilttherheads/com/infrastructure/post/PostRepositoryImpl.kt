package owlstilttherheads.com.infrastructure.post

import kotlinx.serialization.json.Json
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.statement.StatementContext
import owlstilttherheads.com.domain.exception.InfrastructureException
import owlstilttherheads.com.domain.post.Post
import owlstilttherheads.com.infrastructure.Database
import owlstilttherheads.com.usecase.post.PostRepository
import java.sql.ResultSet
import java.time.OffsetDateTime
import java.util.*

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
                throw InfrastructureException("error loading all post", e)
            }
        }
    }

    override fun findById(id: UUID): Post? {
        return jdbi.withHandle<Post, Exception> { handle ->
            val query = """
                SELECT 
                    id, 
                    created_at, 
                    updated_at, 
                    content::varchar as content_j, 
                    is_open, 
                    "order" 
                FROM post
                WHERE id = :id
            """.trimIndent()

            try {
                val result = handle.createQuery(query)
                    .bind("id", id)
                    .map { rs, ctx -> postRowMapper(rs, ctx) }
                    .singleOrNull()

                handle.commit()
                return@withHandle result
            } catch (e: Exception) {
                handle.rollback()
                throw InfrastructureException("error finding post with id: $id", e)
            }
        }
    }

    override fun search(keywords: String): List<Post> {
        val query = """
            SELECT 
                id, 
                created_at, 
                updated_at, 
                content::varchar as content_j, 
                is_open, 
                "order"
            FROM post
            WHERE content &@~ :keywords
            ORDER BY post.order DESC;
        """.trimIndent()

        return jdbi.withHandle<List<Post>, Exception> { handle ->
            try {
                val result = handle.createQuery(query)
                    .bind("keywords", keywords)
                    .map { rs, ctx -> postRowMapper(rs, ctx) }
                    .list()

                // See: https://stackoverflow.com/questions/38579231/hikaricp-select-queries-execute-roll-back-due-to-dirty-commit-state-on-close
                handle.commit()
                return@withHandle result
            } catch (e: Exception) {
                handle.rollback()
                throw InfrastructureException("error searching post with keywords: $keywords", e)
            }
        }
    }

    override fun update(handle: Handle, post: Post) {
        val query = """
                UPDATE post 
                    set updated_at = :updated_at, 
                    content = CAST(:content AS JSONB), 
                    is_open = :isOpen
                WHERE id = :id
            """.trimIndent()

        try {
            handle.createUpdate(query)
                .bind("id", post.id)
                .bind("updated_at", post.updatedAt)
                .bind("content", post.content.toString())
                .bind("isOpen", post.isOpen)
                .execute()

            handle.commit()
        } catch (e: Exception) {
            handle.rollback()
            throw InfrastructureException("error updating post $post", e)
        }
    }

    override fun create(handle: Handle, post: Post) {
        val query = """
            INSERT INTO post (
                id,
                content,
                is_open
            ) VALUES (
                :id,
                CAST(:content AS JSONB),
                :isOpen
            )
        """.trimIndent()

        try {
            handle.createUpdate(query)
                .bind("id", post.id)
                .bind("content", post.content.toString())
                .bind("isOpen", post.isOpen)
                .execute()

            handle.commit()
        } catch (e: Exception) {
            handle.rollback()
            throw InfrastructureException("error creating post $post", e)
        }
    }

    override fun delete(handle: Handle, id: UUID) {
        val query = """
            DELETE
            FROM post
            WHERE id = :id
        """.trimIndent()

        try {
            handle.createUpdate(query)
                .bind("id", id)
                .execute()

            handle.commit()
        } catch (e: Exception) {
            handle.rollback()
            throw InfrastructureException("error deleting post id: $id", e)
        }
    }

    private fun postRowMapper(rs: ResultSet, ctx: StatementContext): Post {
        val id = rs.getString("id")
        val createdAt = rs.getObject("created_at", OffsetDateTime::class.java)
        val updatedAt = rs.getObject("updated_at", OffsetDateTime::class.java)
        val content = rs.getString("content_j")
        val isOpen = rs.getBoolean("is_open")
        val order = rs.getInt("order")

        return Post.restore(
            id = UUID.fromString(id),
            createdAt = createdAt,
            updatedAt = updatedAt,
            content = Json.parseToJsonElement(content),
            isOpen = isOpen,
            order = order
        )
    }
}