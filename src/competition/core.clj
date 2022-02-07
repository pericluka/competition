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
            [competition.controller.controller :as controller]
            [ring.middleware.session :as session]
            [competition.domain.users :as users]
            [competition.domain.players :as players]))

(defroutes app-routes
           (GET "/" [] (controller/index))
           (GET "/team/:id" [id] (controller/teamPage id))
           (GET "/login" [:as {session :session}]
             (if (:admin session)
               (resp/redirect "/")
               (controller/loginPage)))
           (POST "/login" [username password]
             (if (users/loginUser username password)
               (-> (resp/redirect "/")
                   (assoc-in [:session :admin] true))
               (controller/loginPage)))
           (GET "/logout" []
             (-> (resp/redirect "/")
                 (assoc-in [:session :admin] false)))
            (GET "/player/:id" [id] (controller/playerPage id)))

(defroutes admin-routes
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
           (GET "/players/new" [] (controller/editPlayerPage nil))
           (GET "/player/:id/edit" [id] (controller/editPlayerPage id))
           (GET "/player/:id/delete" [id] (do (players/delete id)
                                            (resp/redirect "/")))
           (POST "/players/:id" [id & params] (let  [player {:name (:name params)
                                                             :dateofbirth (:dateofbirth params)
                                                             :placeofbirth (:placeofbirth params)
                                                             :heightincm (Integer/parseInt (:heightincm params))
                                                             :playerposition (Integer/parseInt (:playerposition params))
                                                             :team (Integer/parseInt (:team params))
                                                             :playernumber (Integer/parseInt (:playernumber params))}]
                                                (do (players/update id player)
                                                  (resp/redirect "/"))))
           (POST "/players" [& params] (let  [player {:name (:name params)
                                                             :dateofbirth (:dateofbirth params)
                                                             :placeofbirth (:placeofbirth params)
                                                             :heightincm (Integer/parseInt (:heightincm params))
                                                             :playerposition (Integer/parseInt (:playerposition params))
                                                             :team (Integer/parseInt (:team params))
                                                             :playernumber (Integer/parseInt (:playernumber params))}]
                                                (do (players/create player)
                                                    (resp/redirect "/")))))

(defn wrap-admin-only [handler]
  (fn [request]
    (if (-> request :session :admin)
      (handler request)
      (resp/redirect "/login"))))

(def app
  (-> (routes (wrap-routes admin-routes wrap-admin-only) app-routes)
      (wrap-defaults site-defaults)
      session/wrap-session))


