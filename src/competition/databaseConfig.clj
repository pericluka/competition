(ns competition.databaseConfig)

(def dbConn {:subprotocol "mysql"
                      :subname "//localhost:3306/competiton?characterEncoding=utf8"
                      :user "root"
                      :password ""
                      :zeroDateTimeBehaviour "convertToNull"})