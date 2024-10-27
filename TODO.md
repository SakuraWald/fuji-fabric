# TODO

- feature: a rank module which is based on luckperms track concept. The rank system should allow to custom the
  requirement/criteria, promote event, de-promote event.
  - node
    - name
    - display name
    - requirement -> based on placeholder
    - on enter
    - on leave
    - is default
    - auto rank up
- feature: a lisp-like DSL, including a parse, transformer, analyzer, code-walker, with some built-in functions:
  predicate,
  equal...
  - similar: skript, kubejs, command macro
  - interop: /lisp eval
  - special form: setf, if, lambda, progn, quote
  - data type:
    - numeric
    - bool
    - string
    - cons
    - structure
  - domain entities:
    - entity
    - block
    - item
  - pre-define symbols: 
    - variable 
    - function
      - repl
      - list operations
      - json operations
      - text operation
  - application: /air, /alert, /respawn, /vote

- docs: remove the command level in docs