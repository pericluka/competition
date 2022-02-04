(ns competition.domain.teams
  (:refer-clojure :exclude [update])
  (:require [competition.databaseConfig :as db]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(defn getAll []
  (jdbc/query db/dbConn ["SELECT * FROM teams"]))
