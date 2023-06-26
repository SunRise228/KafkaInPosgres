CREATE TABLE webuser
    (
        id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
        username VARCHAR(255) NOT NULL,
        password VARCHAR(255),
        role VARCHAR(255) NOT NULL
    );

CREATE UNIQUE INDEX webuser_username_idx ON webuser (username);