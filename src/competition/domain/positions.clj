(ns competition.domain.positions
  (:refer-clojure :exclude [update])
  (:require [competition.databaseConfig :as db]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(defn getAll []
  (jdbc/query db/dbConn ["SELECT * FROM positions"]))

(defn getByID [id]
  (first (jdbc/query db/dbConn
                     (sql/select * :positions (sql/where {:positionID id})))))
