CREATE TABLE message
    (
        id UUID NOT NULL PRIMARY KEY,
        country VARCHAR(255) NOT NULL,
        name VARCHAR(255) NOT NULL,
        tin VARCHAR(255) NOT NULL
    );