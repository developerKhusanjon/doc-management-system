-- Database
CREATE DATABASE docmg;
\c docmg;

-- Companies
CREATE TABLE companies (
  id serial NOT NULL,
  PRIMARY KEY (id),
  name character varying NOT NULL,
  address character varying NOT NULL
);

-- Admins
CREATE TABLE admins (
  id uuid NOT NULL,
  name character varying NOT NULL,
  role character varying NOT NULL,
  year_of_employment smallint NOT NULL,
  company_id integer NOT NULL
);

ALTER TABLE admins
ADD CONSTRAINT admins_id PRIMARY KEY (id);
ALTER TABLE admins
ADD FOREIGN KEY (company_id) REFERENCES companies (id);

-- Employees
CREATE TABLE employees (
  id uuid NOT NULL,
  name character varying NOT NULL,
  role character varying NOT NULL,
  year_of_employment smallint NOT NULL,
  company_id integer NOT NULL
);

ALTER TABLE employees
ADD CONSTRAINT employees_id PRIMARY KEY (id);
ALTER TABLE employees
ADD FOREIGN KEY (company_id) REFERENCES companies (id);

-- WebForms
CREATE TABLE web_forms (
  id serial NOT NULL,
  PRIMARY KEY (id),
  title character varying NOT NULL
  content character varying NOT NULL
);