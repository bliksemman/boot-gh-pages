(def project 'bliksemman/boot-gh-pages)
(def +version+ "0.2")

(set-env! :source-paths   #{"src"}
          :resource-paths #{"html"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [boot/core "RELEASE" :scope "test"]
                            [pandeiro/boot-http "0.8.3" :scope "test"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
                            [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[pandeiro.boot-http :refer [serve]])

(bootlaces! +version+)

(task-options!
 pom {:project     project
      :version     +version+
      :description "Boot task for GitHub pages"
      :url         "https://github.com/bliksemman/boot-gh-pages"
      :scm         {:url "https://github.com/bliksemman/boot-gh-pages"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(require '[adzerk.boot-test :refer [test]]
         '[bliksemman.boot-gh-pages :refer [gh-pages]])


(deftask publish-site
  "Update the website."
  []
  (gh-pages :path "html" :ignore #{"*.DS_Store"}))


(deftask run []
  (comp
    (serve :dir "html" :port 4000)
    (watch)
    (target)))
