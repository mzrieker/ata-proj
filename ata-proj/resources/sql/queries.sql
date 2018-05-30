-- :name save-message! :! :n
-- :doc creates a new message
INSERT INTO LOG
(name, message, timestamp)
VALUES (:name, :message, :timestamp)

-- :name get-messages :? :*
-- :doc selects all available messages
SELECT * FROM LOG

-- :name delete-message! :! :n
-- :doc deletes all messages
DELETE * FROM LOG
