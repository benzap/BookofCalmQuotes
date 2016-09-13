(ns bookofcalmquotes.twitter
  (:require [twitter.oauth :refer :all]
            [twitter.callbacks :refer :all]
            [twitter.callbacks.handlers :refer :all]
            [twitter.api.restful :refer :all]
            [taoensso.timbre :as timbre :refer :all]
            [environ.core :refer [env]]))

(def creds (make-oauth-creds
            (env :consumer-key)
            (env :consumer-secret)
            (env :access-token)
            (env :access-secret)))

(defn is-in-bounds? [text]
  (<= (count text) 140))

(defn -tweet [text]
  (try
    (do
      (statuses-update :oauth-creds creds
                       :params {:status text})
      (info "Sent tweet: '" text "'"))
    (catch Exception ex (error (str "Caught Exception: " (.getMessage ex))))))

(defn tweet [text]
  (if (is-in-bounds? text)
    (-tweet text)
    (warn (str "Unable to send tweet " text
               " too many chars [" (count text) " chars]"))))
