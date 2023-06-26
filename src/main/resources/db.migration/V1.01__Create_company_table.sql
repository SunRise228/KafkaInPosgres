CREATE TABLE company
    (
        id UUID NOT NULL PRIMARY KEY,
        country VARCHAR(255) NOT NULL,
        name VARCHAR(255) NOT NULL,
        tin VARCHAR(255) NOT NULL,
        create_time DATE NOT NULL DEFAULT CURRENT_DATE,
        last_update_time DATE NOT NULL DEFAULT CURRENT_DATE
    );