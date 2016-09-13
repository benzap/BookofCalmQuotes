(ns bookofcalmquotes.core
  (:require [environ.core :refer [env]]
            [bookofcalmquotes.db :as db]
            [bookofcalmquotes.twitter :as twitter]
            [bookofcalmquotes.quotes :as quotes]
            [taoensso.timbre :as timbre :refer :all]
            [taoensso.timbre.appenders.core :as appenders]
            [overtone.at-at :as at-at])
  (:gen-class))

;; Configure Logging
(timbre/merge-config!
 {:appenders {:spit (appenders/spit-appender {:fname "resources/log.txt"})}})

;; Scheduler
(def pool (at-at/mk-pool))
(def time-interval (* 1000 60))

(defn tweet-calming-quote []
  (let [quote (db/get-quote)]
    (info "Sending a calming quote...")
    (twitter/tweet quote)))

(defn start-calming []
  (at-at/every time-interval tweet-calming-quote pool))

(at-at/stop-and-reset-pool! pool :strategy :kill)

(defn -main
  "Running will start the scheduler, and start to send calming tweets"
  [& args]
  (info "Initializing Database...")
  (db/init-quotes)
  (info "Updating Quotes...")
  (quotes/init-quote-listing)
  (info "Starting Book of Calm Quotes Twitter Bot")
  (start-calming)
  (while true (Thread/sleep 1000)))
