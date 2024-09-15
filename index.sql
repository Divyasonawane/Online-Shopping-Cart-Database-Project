CREATE INDEX ServicePointCity ON ServicePoint(city);
--SELECT streetaddr, starttime, endtime
--FROM ServicePoint
--WHERE ServicePoint.city IN (
--  SELECT Address.city 
--  FROM Address 
--  WHERE userid = 5);


CREATE INDEX DeliverTime ON Deliver_To(TimeDelivered);

-- SELECT orderNumber
-- FROM Deliver_To
-- WHERE Deliver_To.addrid IN (
--   SELECT Address.addrid 
--   FROM Address 
--   WHERE city = 'Montreal'
-- )
-- AND TimeDelivered < '2017-03-21' 
-- AND TimeDelivered > '2016-03-21';

