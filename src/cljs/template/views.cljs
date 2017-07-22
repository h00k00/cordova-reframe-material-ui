(ns template.views
  (:require
    [cljsjs.material-ui]
    [cljs-react-material-ui.core :refer [get-mui-theme color]]
    [cljs-react-material-ui.reagent :as ui]
    [cljs-react-material-ui.icons :as ic]
    [secretary.core :as secretary]
    [re-frame.core :as re]
    [reagent.core :as r]))

(defn home-panel []
  (fn []
    [:div "This is Home Page."
     [:div "Home"]
     ]))

(defn item2-panel []
  (fn []
    [:div "This is the Item 2 Page."
     [:div [:a {:href "#/"} "go to Home Page"]]
     ]))

(defn item3-panel []
 (fn []
   [:div "This is the Item 3 Page."
    [:div [:a {:href "#/"} "go to Home Page"]]
    ]))

(defn item4-panel []
  (fn []
    [:div "This is the Item 4 Page."
     [:div [:a {:href "#/"} "go to Home Page"]]
     ]))

(defn item5-panel []
 (fn []
   [:div "This is the Item 5 Page."
    [:div [:a {:href "#/"} "go to Home Page"]]
    ]))

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :item2-panel [] [item2-panel])
(defmethod panels :item3-panel [] [item3-panel])
(defmethod panels :item4-panel [] [item4-panel])
(defmethod panels :item5-panel [] [item5-panel])
(defmethod panels :default [] [home-panel])


(defn main-panel []
  (let [name (re/subscribe [:name])
        drawer-open (re/subscribe [:drawer-open])
        active-panel (re/subscribe [:active-panel])]
    (fn []
      [ui/mui-theme-provider
        {:mui-theme (get-mui-theme
          {:palette {:text-color (color :green600)}})}
        [:div
          [ui/app-bar { :title "Title"
                        :icon-element-right
                          (r/as-element [ui/icon-button
                            (ic/action-account-balance-wallet)])
                        :on-left-icon-button-touch-tap
                          #(re/dispatch [:open-drawer])}]
          [ui/drawer
            {:docked            false
             :open              (if @drawer-open true false)
             :on-request-change #(re/dispatch [:close-drawer])}
            [ui/menu-item {:primary-text "Home"
                           :href "#/"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 1 Clicked"))}]
            [ui/menu-item {:primary-text "Menu Item 2"
                           :href "#/item2"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 2 Clicked"))}]
            [ui/menu-item {:primary-text "Menu Item 3"
                           :href "#/item3"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 3 Clicked"))}]
            [ui/menu-item {:primary-text "Menu Item 4"
                           :href "#/item4"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 4 Clicked"))}]
            [ui/menu-item {:primary-text "Menu Item 5"
                           :href "#/item5"
                           :on-touch-tap
                            (fn []
                              (println "Menu Item 5 Clicked"))}]]
          [ui/paper
            (panels @active-panel)]
          [ui/paper
            [ui/mui-theme-provider
              {:mui-theme (get-mui-theme {:palette {:text-color (color :blue200)}})}
            [ui/raised-button {:label "Blue button"}]]
            (ic/action-home {:color (color :grey600)})
            [ui/raised-button { :label        "Click me"
                                :icon         (ic/social-group)
                                :on-touch-tap #(println "clicked")}]]]])))
