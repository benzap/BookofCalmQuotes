(ns bookofcalmquotes.quotes
  (:require [bookofcalmquotes.db :as db]
            [bookofcalmquotes.twitter :as twitter]
            [taoensso.timbre :as timbre :refer :all]))

(defn insert-quote! [text]
  (cond
    (db/has-quote? text)
    (warn "The calming quote '" text "' is already in the db")
    (not (twitter/is-in-bounds? text))
    (warn "Tweet is not within the 140 char limit actual: " (count text))
    :else (db/insert-quote text)))

(defn init-quote-listing [qs]
  (doseq [quote qs]
    (insert-quote! quote)))

(init-quote-listing
 [
"Concentrate on silence.
When it comes, dwell on what it sounds like."

"Do your stress levels a favour and take on changes one at a time."
  
"Eat more fruit, and you'll feel more relaxed - it's as sweet as that."

  ])
