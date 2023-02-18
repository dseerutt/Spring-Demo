# Spring Demo app plan

This is a computer buying app:

- The client can buy computers only if there are enough
- The provider can provision/deprovision computer until it reaches the limit
- Computers have 3 types: Windows, Mac, Linux

## Webservices endpoints

1. GET / -> Get index page
2. GET /client/computer -> Gets the available computers and their description
4. POST /client/sale/{id} -> Buys the computer
5. GET /provider/computer -> Gets the available computer, their description and the stock
6. POST /provider/computer/provision -> Provision X new computer to the store from any type
7. POST /provider/computer/deprovision -> Deprovision X new computer from any type

## Data structures

Computer:

- int id
- String brand
- String version
- String description
- double price

Computer Store:

- int id
- Computer computer
- int stock
- Date lastProvisionDate

Sale:

- int id
- String clientName
- Computer computer
- int quantity
- Date saleDate

## New webservice ideas

- Make the computer limit modifiable
- Add the store concept (is not available at that store)
- Add the money concept
- Add a complaint webservice