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
           (GET "/" [] controller/index)
           (route/not-found "Not found"))

(def app
  (wrap-defaults app-routes site-defaults))


