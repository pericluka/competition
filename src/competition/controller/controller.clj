(ns competition.controller.controller
  (:require
    [clj-http.client :as client]
    [clojure.java.io :as io]
    [competition.domain.teams :as teams]
    [hiccup.page :refer [html5]]))

(def teamsC (teams/getAll))

(defn base-page [& body]
  (html5
    [:head [:title "Teams"]]
    [:body
     [:h1 "Teams1"]
     body]))

(defn index [_]
  (base-page (for [t teamsC]
               [:h2 (:fullname t)])))


