(ns url64.core
  (:require [clojure.data.codec.base64 :as base64]
            [clojure.string :as str]))

(defn uuid4 [] (java.util.UUID/randomUUID))

(defn uuid-as-byte-array []
  "Generate uuid as 16 byte array"
  (let [u (uuid4)
        ^long lo (.getLeastSignificantBits u)
        ^long hi (.getMostSignificantBits u)]
    (-> (java.nio.ByteBuffer/allocate 16)
      (.putLong hi)
      (.putLong lo)
      (.array))))
 
(defn bytes-to-base64-str [bytes]
  (-> bytes base64/encode (String. "UTF-8")))

(defn generate-id []
  (let [uuid (bytes-to-base64-str (uuid-as-byte-array))]
    (clojure.string/replace (clojure.string/replace uuid #"[\+\/]" "-") #"==$" "")))
