CREATE USER "pg-invest" WITH PASSWORD 'invest-password';

CREATE DATABASE "invest";

ALTER DATABASE "invest" OWNER TO "pg-invest";

\connect "invest";
GRANT ALL PRIVILEGES ON DATABASE "invest" TO "pg-invest";
GRANT ALL ON SCHEMA public TO "pg-invest";