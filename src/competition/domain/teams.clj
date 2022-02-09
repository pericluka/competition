(ns competition.domain.teams
  (:refer-clojure :exclude [update])
  (:require [competition.databaseConfig :as db]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(defn getAll []
  (jdbc/query db/dbConn ["SELECT * FROM teams"]))

(defn getByID [id]
  (first (jdbc/query db/dbConn
                     (sql/select * :teams (sql/where {:teamID id})))))

(defn getByName [name]
  (first (jdbc/query db/dbConn
                     (sql/select * :teams (sql/where {:fullName name})))))

(defn create [params]
  (jdbc/insert! db/dbConn :teams params))

(defn update [id params]
  (jdbc/update! db/dbConn :teams params (sql/where {:teamID id})))

(defn delete [id]
  (jdbc/delete! db/dbConn :teams (sql/where {:teamID id})))