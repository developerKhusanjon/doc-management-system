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
  description varchar NOT NULL
);

CREATE TABLE form_submissions (
    id serial PRIMARY KEY,
    input_data varchar NOT NULL,
    web_form_id REFERENCES web_forms
)