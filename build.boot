(def project 'bliksemman/boot-gh-pages)
(def +version+ "0.1")

(set-env! :source-paths   #{"src"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [boot/core "RELEASE" :scope "test"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
                            [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[adzerk.bootlaces :refer :all])

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
