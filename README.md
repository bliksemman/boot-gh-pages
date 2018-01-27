# boot-gh-pages

A Boot task to publish the contents of a folder to GitHub pages.

## Usage

To use this in your project, add `[bliksemman/boot-gh-pages "0.1"]` to your `:dependencies`
and then require the task:

    (require '[bliksemman.boot-gh-pages :refer [gh-pages]])

Add the task after any build related commands, for instance:

    (deftask build []
      (comp
        (cljs :optimizations :advanced)
        (target)
        (gh-pages :path "target")))

The `:path` argument must point to a directory. Anything in that directory will
be published to the gh-pages branch. The branch will be pushed (*with force*)
to the default remote.

## Ignore

Ignore patterns can also be specified using glob syntax. Set the `:ignore` parameter
must be a set of such patterns to skip any matching file. The paths that are matched
are relative to the source directory (`:path`).

## License

Copyright © 2018 Jeroen Vloothuis

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
