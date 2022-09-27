(ns clojure.core.specs.alpha-test
  (:require [clojure.spec.alpha :as s]
            [clojure.core.specs.alpha :as cs]
            [clojure.test :refer [deftest is]]))

(deftest reify-spec-test
  (is (nil? (s/explain-data ::cs/reify-args ())))
  (is (nil? (s/explain-data ::cs/reify-args `(:option-a 1))))
  (is (some? (s/explain-data ::cs/reify-args `(:option-a))))
  (is (nil? (s/explain-data ::cs/reify-args `(:option-a 1 :option-b 1))))
  (is (nil? (s/explain-data ::cs/reify-args `(:option-a 1 :option-b 1 ~'Object))))
  (is (nil? (s/explain-data ::cs/reify-args `(~'Object))))
  (is (nil? (s/explain-data ::cs/reify-args `(~'Object MyProtocol))))
  (is (nil? (s/explain-data ::cs/reify-args `(~'Object (~'toString [this#] (str "foo"))))))
  (is (nil? (s/explain-data ::cs/reify-args `(~'Object (~'toString [this#] {:pre [this#]} (str "foo"))))))
  (is (some? (s/explain-data ::cs/reify-args `(~'Object (toString [this#])))))
  (is (some? (s/explain-data ::cs/reify-args `(~'Object (~'toString [])))))
  (is (some? (s/explain-data ::cs/reify-args `(~'Object ([this#] this#)))))
  (is (some? (s/explain-data ::cs/reify-args `(~'Object (~'toString ([this#] (str "foo")))))))
  (is (nil? (s/explain-data ::cs/reify-args `(~'Object
                                               (~'toString [this#] (str "foo"))
                                               MyProtocol
                                               (~'my-protocol-method [this#] (str "foo"))
                                               (~'myProtocol [this#] (str "foo"))))))
  (is (nil? (s/explain-data ::cs/reify-args `(:option-a 1 :option-b 1
                                                        ~'Object
                                                        (~'toString [this#] (str "foo"))
                                                        MyProtocol
                                                        (~'my-protocol-method [this#] (str "foo"))
                                                        (~'myProtocol [this#] (str "foo")))))))
