CREATE TABLE IF NOT EXISTS client_details (
    `clientCode` varchar(255) NOT NULL,
    `clientAddress` varchar(255) DEFAULT NULL,
    `clientEmail` varchar(255) DEFAULT NULL,
    `clientName` varchar(255) DEFAULT NULL,
    `countryLocated` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`clientCode`)
);
