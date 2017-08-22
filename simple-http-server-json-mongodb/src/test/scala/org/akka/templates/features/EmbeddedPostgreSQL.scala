package org.akka.templates.features

import java.io.File
import java.nio.file.Path
import java.util

import ru.yandex.qatools.embed.postgresql.distribution.Version.Main._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object EmbeddedPostgreSQL {
  import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres

  val postgres = new EmbeddedPostgres(V9_5)

  def start = {
    val config = EmbeddedPostgres.cachedRuntimeConfig(new File("/tmp/postgresql").toPath)
    val url: String = postgres.start(config, "localhost", 5432, "users", "user", "password", util.Arrays.asList())

    import java.sql.Connection
    import java.sql.DriverManager

    Class.forName("org.postgresql.Driver")

    val conn: Connection = DriverManager.getConnection(url)
    
    conn.createStatement().execute(
      """
        CREATE SEQUENCE public.users_id_seq
             INCREMENT 1
             START 1
             MINVALUE 1
             MAXVALUE 9223372036854775807
             CACHE 1;
      """)

    conn.createStatement().execute("""ALTER SEQUENCE public.users_id_seq OWNER TO "user";""")

    conn.createStatement().execute(
      """
      CREATE TABLE public.users (id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
                      username character varying(255) COLLATE pg_catalog."default" NOT NULL,
                      age integer NOT NULL,
                      CONSTRAINT users_pkey PRIMARY KEY (id))
                     WITH (
                         OIDS = FALSE
                     )
                     TABLESPACE pg_default;
                     
      ALTER TABLE public.users OWNER to "user";
    """)
  }

  def stop =  postgres.stop()
}
