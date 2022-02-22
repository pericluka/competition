(ns competition.controller.controller
  (:require
    [competition.domain.teams :as teams]
    [hiccup.page :refer [html5]]
    [hiccup.util :as h]
    [hiccup.form :as form]
    [ring.util.anti-forgery :refer [anti-forgery-field]]
    [competition.domain.players :as players]
    [competition.domain.positions :as positions]))


(defn base-page [& body]
  (html5
    [:head [:title "Competition"]
     [:link {:rel "stylesheet"
             :href "https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
             :integrity "sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
             :crossorigin "anonymous"}]]
    [:body {:style "background-image:
    url('https://drive.google.com/uc?export=download&id=1x2wiOYVmaA3VppDAuE16NxZFsDCC1rU6');
    background-repeat: no-repeat;
    background-size: cover;
    background: "}
     [:div.container
      [:nav.navbar.navbar-expand-lg.navbar-light.bd-light
       [:a.navbar-brand {:href "/"} "Home page"]
       [:div.navbar-nav.ml-auto
        [:a.nav-item.nav-link {:href "/teams/new"} "New team"]
        [:a.nav-item.nav-link {:href "/players/new"} "New player"]
        [:a.nav-item.nav-link {:href "/login"} "Login"]
        [:a.nav-item.nav-link {:href "/logout"} "Logout"]
        ]]
      body]
      ]
     ))


(defn index []
  (base-page
    [:hr]
    [:h1 "Teams:"]
    (let [teamsColl (teams/getAll)]
               (for [t teamsColl]
                 [:h2 [:a {:href (str "/team/" (:teamid t))} (:fullname t)]]))))

(defn teamPage [id]
  (let [t (teams/getByID id)]
    (base-page
      [:hr]
      [:h1 (str (:fullname t) ,)]
      [:p (str "also known as the " (:nickname t)
               ", is a professional football club founded in "
                (:founded t) ". The club is playing at the " (:ground t)
                 " whose capacity is " (:capacity t) ".")]
      (form/form-to
        [:delete (str "/team/" id "/delete")]
        (anti-forgery-field)
        [:a.btn.btn-primary {:href (str "/team/" id "/edit")} "Edit team"]
        (form/submit-button {:class "btn btn-danger"} "Delete team"))
      [:hr]
      [:h2 "Players in the team:"]
      (let [playersColl (players/getPlayersFromTeam id)]
        (for [p playersColl]
          [:h2 [:a {:href (str "/player/" (:playerid p))} (str (:playernumber p) " " (:name p))]])))))

(defn editTeamPage [id]
  (let [t (teams/getByID id)]
    (base-page
      [:hr]
      (form/form-to
        [:post (if t
                 (str "/teams/" (:teamid t))
                 "/teams")]

        [:div.form-group
         (form/label "fullname" "Fullname")
         (form/text-field {:class "form-control"} "fullname" (:fullname t))

         (form/label "nickname" "Nickname")
         (form/text-field {:class "form-control"} "nickname" (:nickname t))

         (form/label "founded" "Founded")
         (form/text-field {:class "form-control"} "founded" (:founded t))

         (form/label "ground" "Ground")
         (form/text-field {:class "form-control"} "ground" (:ground t))

         (form/label "capacity" "Capacity")
         (form/text-field {:class "form-control"} "capacity" (:capacity t))]


        (anti-forgery-field)

        (form/submit-button {:class "btn btn-primary"} "Save")
        ))))

(defn loginPage [& [msg]]
  (base-page
    [:hr]
    (when msg
      [:div.alert.alert-danger msg])
    (form/form-to
      [:post "/login"]

      [:div.form-group
       (form/label "username" "Username")
       (form/text-field {:class "form-control"} "username")]

      [:div.form-group
       (form/label "password" "Password")
       (form/password-field {:class "form-control"} "password")]

      (anti-forgery-field)

      (form/submit-button {:class "btn btn-primary"} "Login")
      )))


(defn playerPage [id]
  (let [p (players/getByID id)]
    (base-page
      [:hr]
      [:h1 (str (:name p) ,)]
      [:p (str  "is a professional football player born in "
                (subs (str (:dateofbirth p)) 0 4) " in " (:placeofbirth p) ". He is a successful, " (:heightincm p)
                "cm tall " (:name (positions/getByID (:playerposition p))) " who has number " (:playernumber p)
                " on " (:nickname (teams/getByID (:team p))) " jersey.")]
      (form/form-to
        [:delete (str "/player/" id "/delete")]
        (anti-forgery-field)
        [:a.btn.btn-primary {:href (str "/player/" id "/edit")} "Edit player"]
        (form/submit-button {:class "btn btn-danger"} "Delete player"))


      )))



(defn editPlayerPage [id]
  (let [p (players/getByID id)]
    (base-page
      [:hr]
      (form/form-to
        [:post (if p
                 (str "/players/" (:playerid p))
                 "/players")]

        [:div.form-group

         (form/label "name" "Name")
         (form/text-field {:class "form-control"} "name" (:name p))

         (form/label "dateofbirth" "Date of birth (YYYY-MM-DD)")
         (form/text-field {:class "form-control"} "dateofbirth" (:dateofbirth p))

         (form/label "placeofbirth" "Place of birth")
         (form/text-field {:class "form-control"} "placeofbirth" (:placeofbirth p))

         (form/label "heightincm" "Height in cm")
         (form/text-field {:class "form-control"} "heightincm" (:heightincm p))

         (form/label "playerposition" "Player position")
         (form/drop-down {:class "form-control"} "playerposition" (let [allPositions (positions/getAll)]
                                            (for [position allPositions]
                                              [(:name position)])))

         (form/label "team" "Team")
         (form/drop-down {:class "form-control"} "team" (let [allTeams (teams/getAll)]
                                  (for [team allTeams]
                                    [(:fullname team)])))

         (form/label "playernumber" "Player number")
         (form/text-field {:class "form-control"} "playernumber" (:playernumber p))]

         (anti-forgery-field)

         (form/submit-button {:class "btn btn-primary"} "Save")

        ))))
