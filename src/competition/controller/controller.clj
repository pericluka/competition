(ns competition.controller.controller
  (:require
    [clj-http.client :as client]
    [clojure.java.io :as io]
    [competition.domain.teams :as teams]
    [hiccup.page :refer [html5]]
    [hiccup.form :as form]
    [ring.util.anti-forgery :refer [anti-forgery-field]]))


(defn base-page [& body]
  (html5
    [:head [:title "Teams"]]
    [:a {:href "/teams/new"} "New team!"]
    [:hr]
    [:body
     [:a {:href "/"} [:h1 "Teams home page"]]
     body]))

(defn index []
  (base-page (let [teamsColl (teams/getAll)]
               (for [t teamsColl]
                 [:h2 [:a {:href (str "/team/" (:teamid t))} (:fullname t)]]))))

(defn teamPage [id]
  (let [t (teams/getByID id)]
    (base-page
      [:a {:href (str "/team/" id "/edit")} "Edit team"]
      [:br]
      [:a {:href (str "/team/" id "/delete")} "Delete team"]
      [:hr]
      [:small (:founded t)]
      [:h1 (:fullname t)]
      [:p (:nickname t)])))

(defn editTeamPage [id]
  (let [t (teams/getByID id)]
    (base-page
      (form/form-to
        [:post (if t
                 (str "/teams/" (:teamid t))
                 "/teams")]

        (form/label "fullname" "Fullname")
        (form/text-area "fullname" (:fullname t))

        (form/label "nickname" "Nickname")
        (form/text-field "nickname" (:nickname t))

        (form/label "founded" "Founded")
        (form/text-field "founded" (:founded t))

        (form/label "ground" "Ground")
        (form/text-field "ground" (:ground t))

        (form/label "capacity" "Capacity")
        (form/text-field "capacity" (:capacity t))

        (anti-forgery-field)

        (form/submit-button "Save")
        ))))


