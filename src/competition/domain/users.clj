(ns competition.domain.users)

(def adminUser "admin")
(def adminPass "admin")

(defn loginUser [username password]
  (and (= username adminUser)
       (= password adminPass)))
