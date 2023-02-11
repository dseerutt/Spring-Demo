# Spring Demo app plan

This is a computer buying app:

- The client can buy computers only if there are enough
- The provider can provision/deprovision computer until it reaches the limit
- Computers have 3 types: Windows, Mac, Linux

## Webservices endpoints

1. GET / -> Get index page
2. GET /client/stock -> Gets the available computers and their description
4. POST /client/computer/{id} -> Buys the computer
5. GET /provider/stock -> Gets the available computer store stock
5. GET /provider/stock/{id} -> Gets the available computer store stock from the selected type
6. POST /provider/provision -> Provision X new computer to the store from any type
7. POST /provider/provision -> Deprovision X new computer from any type

## Data structures

Computer:

- String brand
- String description
- Integer price

Computer Store:

- Computer computer
- String stock
- Date last date of provision

Sale:

- String clientName
- ComputerBrand computer brand
- String number
- Date date of sale

## New webservice ideas

- Make the computer limit modifiable
- Add the store concept (is not available at that store)
- Add the money concept
- Add a complaint webservice