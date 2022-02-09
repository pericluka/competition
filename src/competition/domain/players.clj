(ns competition.domain.players
  (:refer-clojure :exclude [update])
  (:require [competition.databaseConfig :as db]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(defn getAll []
  (jdbc/query db/dbConn ["SELECT * FROM players"]))

(defn getPlayersFromTeam[teamID]
  (jdbc/query db/dbConn
              (sql/select * :players (sql/where {:team teamID}))))

(defn getByID [id]
  (first (jdbc/query db/dbConn
                     (sql/select * :players (sql/where {:playerID id})))))

(defn create [params]
  (jdbc/insert! db/dbConn :players params))

(defn update [id params]
  (jdbc/update! db/dbConn :players params (sql/where {:playerID id})))

(defn delete [id]
  (jdbc/delete! db/dbConn :players (sql/where {:playerID id})))
