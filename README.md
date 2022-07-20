# RPC-Communication

This project consists of an platform dedicated for clients, that have intelligent home appliances that can be controlled remotely. Each such device can communicate (using remote procedure call (RPC)) with the server that will compute the time when the device will be started for an optimal energy consumption. <br />
The client-side application (a desktop application) is associated for the smart appliance of a client. This app has the roll to: <br />
  - gets the client hourly historical energy consumption.
  - gets the averaged energy consumption for the client.
  - gets the best time to be started considering the baseline and the program duration to avoid energy peaks from the client.
  
Technologies used: <br />
  - For backend application (Java Spring) <br />
  - RPC framework (JAVA RMI). <br />

Requirements: <br />
  - The client application displays a chart with the client historical energy consumption <br />
  - The client application displays the client baseline as a reference consumption for the next day <br />
  - The client application asks the server for the best start time in the next day to minimize the peaks of energy consumption <br />
