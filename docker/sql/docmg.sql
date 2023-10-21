-- Database
CREATE DATABASE docmg;
\c docmg;

-- Companies
CREATE TABLE companies (
  id serial PRIMARY KEY,
  name varchar(100) NOT NULL,
  address varchar NOT NULL
);

-- Employees
CREATE TABLE employees (
  id serial PRIMARY KEY,
  name varchar(100) NOT NULL,
  role varchar(100) NOT NULL,
  company_id integer REFERENCES companies
);

-- WebForms
CREATE TABLE web_forms (
  id serial PRIMARY KEY,
  title varchar(100) NOT NULL
);

CREATE TABLE form_fields (
    id serial PRIMARY KEY,
    title varchar NOT NULL,
    is_required BOOLEAN NOT NULL DEFAULT FALSE,
    web_form_id REFERENCES web_forms
)

CREATE TABLE form_submissions (
    id serial PRIMARY KEY,
    submit_date DATE NOT NULL DEFAULT CURRENT_DATE,
    web_form_id REFERENCES web_forms
)

CREATE TABLE field_values (
    id serial PRIMARY KEY,
    input_data varchar,
    form_field_id REFERENCES form_fields,
    form_submission_id REFERENCES form_submissions
)