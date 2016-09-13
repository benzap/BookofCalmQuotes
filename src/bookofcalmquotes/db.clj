(ns bookofcalmquotes.db
  (:require [environ.core :refer [env]]
            [clojure.java.jdbc :as jdbc]
            [taoensso.timbre :as timbre :refer :all]))

(def conn (env :database-url))

(defn table-exists? [name]
  (let [q
        (jdbc/query conn
                    ["SELECT *
                      FROM INFORMATION_SCHEMA.TABLES 
                      WHERE TABLE_NAME = ?" name])]
    (not (empty? q))))

(def table-quotes
  "CREATE TABLE quotes (
     qid SERIAL PRIMARY KEY,
     quote TEXT UNIQUE,
     last_quoted TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   )")

(defn init-quotes []
  (when-not (table-exists? "quotes")
    (info "Creating quotes table...")
    (jdbc/execute! conn table-quotes)))

(defn insert-quote [text]
  (let [q "INSERT INTO quotes (quote) VALUES (?)"]
    (jdbc/execute! conn [q text])))

(defn has-quote? [text]
  (let [q "SELECT 1 FROM quotes WHERE quote = ?"]
    (-> (jdbc/query conn [q text]) first empty? not)))

#_(insert-quote "This is another test quote??")
#_(has-quote? "This is another test quote?")
#_(init-quotes)

(defn -get-quote []
  (let [q "SELECT qid, quote
           FROM quotes
           ORDER BY last_quoted ASC
           LIMIT 1"]
    (-> (jdbc/query conn q) first)))

(defn -update-quote-time [id]
  (let [q "UPDATE quotes
           SET last_quoted = CURRENT_TIMESTAMP
           WHERE qid = ?"]
    (jdbc/execute! conn [q id])))

#_(-get-quote)

(defn get-quote []
  (let [{:keys [qid quote]} (-get-quote)]
    (-update-quote-time qid)
    quote))

#_(get-quote)
