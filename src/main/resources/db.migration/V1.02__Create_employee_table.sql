CREATE TABLE employee
    (
        id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
        company_id UUID NOT NULL,
        supervisor_id UUID,
        name VARCHAR(255) NOT NULL,
        salary INTEGER NOT NULL,
        email VARCHAR(255) NOT NULL,
        role VARCHAR(255) NOT NULL,
        create_time DATE NOT NULL DEFAULT CURRENT_DATE,
        last_update_time DATE NOT NULL DEFAULT CURRENT_DATE,
        CONSTRAINT fk_company
            FOREIGN KEY(company_id)
                REFERENCES company(id),
        CONSTRAINT fk_supervisor
                    FOREIGN KEY(supervisor_id)
                        REFERENCES employee(id)
    );

CREATE UNIQUE INDEX employee_email_idx ON employee (email);
