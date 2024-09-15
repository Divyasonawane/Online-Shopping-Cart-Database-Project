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

* [Table.sql]
* [Insert.sql]
* [Modification.sql]
# Java GUI
