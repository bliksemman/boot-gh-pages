(ns bliksemman.boot-gh-pages
  "Example tasks showing various approaches."
  {:boot/export-tasks true}
  (:require [boot.core :as boot :refer [deftask]]
            [boot.util :as util]
            [boot.file :as file]
            [boot.core :as boot]
            [boot.util :as util]
            [clojure.java.shell :as shell]
            [clojure.string :as string]))

(defn- string-input-stream [s]
  (java.io.ByteArrayInputStream.
   (.getBytes s (.name java.nio.charset.StandardCharsets/UTF_8))))

(defn- git-modified-line [root-path f]
  (format "M 644 inline %s\ndata %s \n"
          (->> (.toPath f)
               (.toAbsolutePath)
               (.relativize root-path)
               (.toString))
          (.length f)))

(def git-import-header
 (string/join "\n"
  ["commit refs/heads/gh-pages"
   "committer Boot GH-Pages <info@example.com> now"
   "data 0"
   ""]))

(defn- git-import-data [path]
 (java.io.SequenceInputStream.
  (string-input-stream git-import-header)
  (->> (file/file-seq (java.io.File. (.toUri path)))
       (filter file/file?)
       (map (fn [f]
             (do
               (util/info "• %s\n" f)
               f)))
       (reduce
        (fn [c f]
          (java.io.SequenceInputStream.
           c
           (java.io.SequenceInputStream.
            (string-input-stream (git-modified-line path f))
            (java.io.SequenceInputStream.
              (java.io.FileInputStream. f)
              (string-input-stream "\n")))))
        (string-input-stream "")))))

(defn- map-to-git-import [path]
  "Creates git seq with lines for a git import."
  (let [root-path (.toAbsolutePath (java.nio.file.Paths/get path (make-array String 0)))]
    (let [output (shell/sh "git" "fast-import" "--date-format=now" "--quiet"
                           "--force"
                           :in (git-import-data root-path))]
      (assert (= (:exit output) 0) (:err output)))))


(boot/deftask gh-pages
  "Update the gh-pages branch with all contents from a given path."
  [p path PATH str "The path of the input folder."]
  (boot/with-pass-thru _
    (util/info "Updating gh-pages branch:\n")
    (map-to-git-import path)
    (util/dosh "git" "push" "origin" "refs/heads/gh-pages" "--force")))
