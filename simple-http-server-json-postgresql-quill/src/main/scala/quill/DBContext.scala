package quill

import com.typesafe.config.Config

import io.getquill._

class DBContext(config: Config) extends PostgresAsyncContext[SnakeCase](SnakeCase, config.getConfig("quill"))