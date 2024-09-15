# Online-Shopping-Cart-Database-Project
# Entities

* User (__userId__, name, phoneNum)
* Buyer (__userId__)
* Seller (__userId__)
* Bank Card (__cardNumber__, userId, bank, expiryDate)
* Credit Card (__cardNumber__, organization)
* Debit Card (__cardNumber__)
* Store (__sid__, name, startTime, customerGrade, streetAddr, city, province)
* Product (__pid__, sid, name, brand, type, amount, price, colour, customerReview, modelNumber)
* Order Item (__itemid__, pid, price, creationTime)
* Order (__orderNumber__, creationTime, paymentStatus, totalAmount)
* Address (__addrid__, userid, name, city, postalCode, streetAddr, province, contactPhoneNumber)

# Relationships

* Manage (__userid__, __sid__, SetupTime) (userid ref Seller, sid ref Store)
* Save to Shopping Cart (__userid__, __pid__, quantity, addtime) (userid ref Buyer, pid ref Product)
* Contain (__orderNumber__, __itemid__, quantity) (orderNumber ref Order, itemid ref Order Item)
* Deliver To (__addrid__, __orderNumber__, TimeDelivered) (addrid ref Address, orderNumber ref Order)
* Payment (__C.cardNumber__, __orderNumber__, payTime) (C.cardNumber ref Credit Card, orderNumber ref Order)

# Create Database

* [Table.sql](https://github.com/Divyasonawane/Online-Shopping-Cart-Database-Project/blob/main/Table.sql):  Create tables for entities and relationships above.
* [Insert.sql](https://github.com/Divyasonawane/Online-Shopping-Cart-Database-Project/blob/main/Insert.sql): Insert datas into tables.
* [Modification.sql](https://github.com/Divyasonawane/Online-Shopping-Cart-Database-Project/blob/main/Modification.sql): Modify the data. 
# Java GUI
[SQL.java](https://github.com/Divyasonawane/Online-Shopping-Cart-Database-Project/blob/main/SQL.java) is  the acutal program that we wrote to submit the sql execution to the database based on the sample above.

[Java_GUI](https://github.com/Divyasonawane/Online-Shopping-Cart-Database-Project/blob/main/Java_GUI) contains the Java programs for creating the GUI.

1. [MainFrame.java](https://github.com/Divyasonawane/Online-Shopping-Cart-Database-Project/blob/main/Java_GUI/MainFrame.java): Provide main menu for user to choose different function.

![image]()

2. [Register.java](https://github.com/Divyasonawane/Online-Shopping-Cart-Database-Project/blob/main/Java_GUI/Register.java): User registration interface.

![image]()

3. [Login.java](): User log-in interface.

![image]()

4. [AddAddress.java](): Add address for delivery.

![image]()

5. [SearchFrame.java]():Search products in database.

![image]()

6. [SaveToCartFrame.java](): Add products into shopping cart.

![image]()

7. [SetUpOrderFrame.java](): View the shopping cart and create the order.

![image]()

8. [addressFrame.java](): Select a delivery address and finish the shopping.

![image]()
