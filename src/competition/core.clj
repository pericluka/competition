(ns competition.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.basic-authentication :refer :all]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as resp]
            [competition.controller.controller :as controller]
            [competition.domain.teams :as teams]
            [ring.adapter.jetty :as ring]
            [hiccup.core :as h]
            [competition.controller.controller :as controller]))

(defroutes app-routes
           (GET "/" [] (controller/index))
           (GET "/team/:id" [id] (controller/teamPage id))
           (GET "/teams/new" [] (controller/editTeamPage nil))
           (GET "/team/:id/edit" [id] (controller/editTeamPage id))
           (POST "/teams/:id" [id & params] (let  [team {:fullname (:fullname params)
                                                  :nickname (:nickname params)
                                                  :founded (Integer/parseInt (:founded params))
                                                  :ground (:ground params)
                                                  :capacity (Integer/parseInt (:capacity params))}]
                                       (do (teams/update id team)
                                           (resp/redirect "/"))))
           (POST "/teams" [& params] (let  [team {:fullname (:fullname params)
                                                  :nickname (:nickname params)
                                                  :founded (Integer/parseInt (:founded params))
                                                  :ground (:ground params)
                                                  :capacity (Integer/parseInt (:capacity params))}]
                                       (do (teams/create team)
                                           (resp/redirect "/"))))
           (GET "/team/:id/delete" [id] (do (teams/delete id)
                                            (resp/redirect "/")))
           (route/not-found "Not found"))

(def app
  (wrap-defaults app-routes site-defaults))


