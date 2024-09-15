-- STORED PROCEDURE
DELIMITER $$

CREATE PROCEDURE func1 (IN vpid INT, IN vbrand VARCHAR(255), OUT v_amount INT)
BEGIN
  -- Compute average amount of the product
  SELECT AVG(amount) INTO v_amount FROM product WHERE brand = vbrand;

  -- Update the quantity of the product in the save_to_shopping_cart table
  UPDATE save_to_shopping_cart 
  SET quantity = v_amount 
  WHERE pid = vpid;
END $$

DELIMITER ;
-- Declaring a session variable to hold the output
CALL func1(8, 'Microsoft', @v_amount);

-- To view the value of @v_amount
SELECT @v_amount;
CREATE TABLE shoppingcart_audits (
  id INT AUTO_INCREMENT PRIMARY KEY,
  userid INT NOT NULL,
  pid INT NOT NULL,
  quantity INT NOT NULL,
  changed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$

CREATE TRIGGER quantity_changes
BEFORE UPDATE ON save_to_shopping_cart
FOR EACH ROW
BEGIN
  IF NEW.quantity <> OLD.quantity THEN
    INSERT INTO shoppingcart_audits (userid, pid, quantity, changed_on)
    VALUES (OLD.userid, OLD.pid, OLD.quantity, NOW());
  END IF;
END $$

DELIMITER ;
