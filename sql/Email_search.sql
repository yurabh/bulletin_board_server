SELECT e.email FROM suitableads as s
INNER JOIN authors as a ON
s.author_fk_id = a.author_id
INNER JOIN emails as e
ON a.author_id = e.author_fk_id
WHERE s.category ='mercedes' AND s.title = 'I wont to buy' AND 202 BETWEEN  s.price_from  AND s.price_to;